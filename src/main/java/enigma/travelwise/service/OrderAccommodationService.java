package enigma.travelwise.service;

import enigma.travelwise.model.OrderAccommodation;
import enigma.travelwise.model.PaymentStatus;
import enigma.travelwise.utils.dto.CustomPage;
import enigma.travelwise.utils.dto.OrderAccommodationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

public interface OrderAccommodationService {
    OrderAccommodation create(OrderAccommodationDTO request);

    CustomPage<OrderAccommodation> getAll(Pageable pageable, Long userId, Integer totalPrice, LocalDate checkIn, LocalDate checkOut);

    OrderAccommodation getOne(String id);

    void updatePaymentStatus(String id, PaymentStatus status);


}
