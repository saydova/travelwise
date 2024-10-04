package enigma.travelwise.service;

import enigma.travelwise.model.OrderAccommodationDetail;
import enigma.travelwise.utils.dto.CustomPage;
import enigma.travelwise.utils.dto.OrderAccommodationDetailDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

public interface OrderAccommodationDetailService {
    OrderAccommodationDetail create(OrderAccommodationDetailDTO request);
    CustomPage<OrderAccommodationDetail> getAll(Pageable pageable, Integer price, Integer quantity, String categoryRoom, Long accommodationId);
    OrderAccommodationDetail getOne(Long id);

}
