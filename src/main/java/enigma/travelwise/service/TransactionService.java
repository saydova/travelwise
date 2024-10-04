package enigma.travelwise.service;

import enigma.travelwise.utils.dto.CreateTransactionResponse;
import enigma.travelwise.utils.dto.OrderAccommodationDTO;
import enigma.travelwise.utils.dto.OrderDestinationDTO;
import enigma.travelwise.utils.dto.Transaction;

import java.util.Map;

public interface TransactionService {
    CreateTransactionResponse createOrderAccommodation(OrderAccommodationDTO request);
    CreateTransactionResponse createOrderDestionation(OrderDestinationDTO request);
    void paymentStatus(String token, String orderId, Transaction.Expiry expiry, String source);
}
