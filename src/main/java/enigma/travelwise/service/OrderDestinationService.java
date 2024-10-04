package enigma.travelwise.service;

import enigma.travelwise.model.OrderDestination;
import enigma.travelwise.model.PaymentStatus;
import enigma.travelwise.utils.dto.CustomPage;
import enigma.travelwise.utils.dto.OrderDestinationDTO;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface OrderDestinationService {
    OrderDestination create(OrderDestinationDTO request);

    CustomPage<OrderDestination> getAll(Pageable pageable, Long userId, Integer totalPrice, LocalDate orderDate);

    OrderDestination getOne(String id);
    void updatePaymentStatus(String id, PaymentStatus status);
}
