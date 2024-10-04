package enigma.travelwise.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import enigma.travelwise.model.OrderAccommodation;
import enigma.travelwise.model.OrderDestination;
import enigma.travelwise.model.PaymentStatus;
import enigma.travelwise.service.OrderAccommodationService;
import enigma.travelwise.service.OrderDestinationService;
import enigma.travelwise.service.TransactionService;
import enigma.travelwise.utils.dto.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Base64;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final RestClient restClient;
    private final ExecutorService executorService;
    private final OrderAccommodationService orderAccommodationService;
    private final OrderDestinationService orderDestinationService;
    @Value("${midtrans.server.key}")
    private String serverKey;

    @Override
    @Transactional
    public CreateTransactionResponse createOrderAccommodation(OrderAccommodationDTO request) {
        OrderAccommodation orderAccommodation = orderAccommodationService.create(request);

        Transaction transaction = Transaction.builder()
                .transactionDetails(
                        Transaction.TransactionDetails.builder()
                                .order_id(orderAccommodation.getId().toString())
                                .gross_amount(orderAccommodation.getTotalPrice())
                                .build()
                )
                .customerDetails(
                        Transaction.CustomerDetails.builder()
                                .first_name(orderAccommodation.getUser().getName())
                                .email(orderAccommodation.getUser().getEmail())
                                .phone_number(orderAccommodation.getUser().getPhone_number())
                                .build()
                )
                .expiry(
                        Transaction.Expiry.builder()
                                .unit("minutes")
                                .duration(2)
                                .build()
                )
                .build();

        CreateTransactionResponse createTransactionResponse = restClient
                .post()
                .uri("https://app.sandbox.midtrans.com/snap/v1/transactions")
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString((serverKey).getBytes()))
                .header("Content-Type", "Application/json")
                .body(transaction)
                .retrieve()
                .body(CreateTransactionResponse.class);

        createTransactionResponse.setOrderId(orderAccommodation.getId().toString());

        log.info("createTransactionResponse: {}", createTransactionResponse);
        executorService.submit(() -> paymentStatus(
                createTransactionResponse.getToken(),
                transaction.getTransactionDetails().getOrder_id(),
                transaction.getExpiry(),
                "accommodation"
        ));

        return createTransactionResponse;
    }

    @Override
    public CreateTransactionResponse createOrderDestionation(OrderDestinationDTO request) {
        OrderDestination orderDestination = orderDestinationService.create(request);

        Transaction transaction = Transaction.builder()
                .transactionDetails(
                        Transaction.TransactionDetails.builder()
                                .order_id(orderDestination.getId().toString())
                                .gross_amount(orderDestination.getTotalPrice())
                                .build()
                )
                .customerDetails(
                        Transaction.CustomerDetails.builder()
                                .first_name(orderDestination.getUser().getName())
                                .email(orderDestination.getUser().getEmail())
                                .phone_number(orderDestination.getUser().getPhone_number())
                                .build()
                )
                .expiry(
                        Transaction.Expiry.builder()
                                .unit("minutes")
                                .duration(2)
                                .build()
                )
                .build();

        CreateTransactionResponse createTransactionResponse = restClient
                .post()
                .uri("https://app.sandbox.midtrans.com/snap/v1/transactions")
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString((serverKey).getBytes()))
                .header("Content-Type", "Application/json")
                .body(transaction)
                .retrieve()
                .body(CreateTransactionResponse.class);

        createTransactionResponse.setOrderId(orderDestination.getId().toString());

        log.info("createTransactionResponse from destination: {}", createTransactionResponse);
        executorService.submit(() -> paymentStatus(
                createTransactionResponse.getToken(),
                transaction.getTransactionDetails().getOrder_id(),
                transaction.getExpiry(),
                "destination"
        ));

        return createTransactionResponse;

    }

    @Override
    public void paymentStatus(String token, String orderId, Transaction.Expiry expiry, String source) {
        if (expiry != null) {
            int durationInSecond = expiry.getDuration() * 60;
            int totalDuration = durationInSecond / 5;
            int countdown = 0;
            for (int i = 0; i < totalDuration; i++) {
                countdown++;
                try {
                    log.warn("countdown: {}", countdown);
                    TransactionDetailDTO transaction = restClient.get()
                            .uri("https://api.sandbox.midtrans.com/v2/" + orderId + "/status")
                            .header("Authorization", "Basic " + Base64.getEncoder().encodeToString((serverKey).getBytes()))
                            .header("Content-Type", "Application/json")
                            .retrieve()
                            .body(TransactionDetailDTO.class);

                    if (transaction != null && transaction.getStatusCode().equals("200")) {
                       log.warn("transaction: {}", transaction.getStatusCode());
                        if (source.equals("accommodation")) {
                            orderAccommodationService.updatePaymentStatus(orderId, PaymentStatus.COMPLETED);
                        } else if (source.equals("destination")) {
                            log.warn("destination");
                            orderDestinationService.updatePaymentStatus(orderId, PaymentStatus.COMPLETED);
                        } else {
                            log.warn("elseeee");
                        }
                        break;
                    }
                    Thread.sleep(5000);
                    if (countdown >= totalDuration) {
                        if (source.equals("accommodation")) {
                            orderAccommodationService.updatePaymentStatus(orderId, PaymentStatus.EXPIRED);
                        } else if (source.equals("destination")) {
                            orderDestinationService.updatePaymentStatus(orderId, PaymentStatus.EXPIRED);
                        }
                        break;
                    }
                } catch (InterruptedException e) {
                    log.warn("Error: {}", e.getMessage());
                    if (source.equals("accommodation")) {
                        orderAccommodationService.updatePaymentStatus(orderId, PaymentStatus.FAILED);
                    } else if (source.equals("destination")) {
                        orderDestinationService.updatePaymentStatus(orderId, PaymentStatus.FAILED);
                    }
                    throw new RuntimeException(e);
                }
            }
        } else {
            log.info("Failed transaction");
            if (source.equals("accommodation")) {
                orderAccommodationService.updatePaymentStatus(orderId, PaymentStatus.FAILED);
            } else if (source.equals("destination")) {
                orderDestinationService.updatePaymentStatus(orderId, PaymentStatus.FAILED);
            }
        }
    }
}
