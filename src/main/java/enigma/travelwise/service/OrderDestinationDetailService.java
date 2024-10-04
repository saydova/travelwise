package enigma.travelwise.service;

import enigma.travelwise.model.OrderAccommodationDetail;
import enigma.travelwise.model.OrderDestinationDetail;
import enigma.travelwise.utils.dto.CustomPage;
import enigma.travelwise.utils.dto.OrderAccommodationDetailDTO;
import enigma.travelwise.utils.dto.OrderDestinationDetailDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface OrderDestinationDetailService {
    OrderDestinationDetail create(OrderDestinationDetailDTO request);
    CustomPage<OrderDestinationDetail> getAll(Pageable pageable, Integer price, Integer quantity, String categoryTicket, Long destinationId);
    OrderDestinationDetail getOne(Long id);

}
