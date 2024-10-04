package enigma.travelwise.service.impl;

import enigma.travelwise.model.Accommodation;
import enigma.travelwise.model.OrderAccommodation;
import enigma.travelwise.model.OrderAccommodationDetail;
import enigma.travelwise.model.OrderDestination;
import enigma.travelwise.repository.OrderAccommodationDetailRepository;
import enigma.travelwise.service.AccommodationService;
import enigma.travelwise.service.OrderAccommodationDetailService;
import enigma.travelwise.utils.dto.CustomPage;
import enigma.travelwise.utils.dto.OrderAccommodationDetailDTO;
import enigma.travelwise.utils.specification.OrderAccommodationDetailSpecification;
import enigma.travelwise.utils.specification.OrderAccommodationSpecification;
import enigma.travelwise.utils.specification.OrderDestinationSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderAccommodationDetailServiceImpl implements OrderAccommodationDetailService {
    private final OrderAccommodationDetailRepository orderAccommodationDetailRepository;
    private final AccommodationService accommodationService;

    @Override
    public OrderAccommodationDetail create(OrderAccommodationDetailDTO request) {
        OrderAccommodationDetail newOrder = new OrderAccommodationDetail();
        Accommodation accommodation = accommodationService.getById(request.getAccommodationId());
        OrderAccommodation orderAccommodation = request.getOrderAccommodation();

        newOrder.setQuantity(request.getQuantity());
        int priceAfterQuantity = request.getQuantity() * accommodation.getCategoryPrices().get(request.getCategory());
        newOrder.setPrice(priceAfterQuantity);
        newOrder.setAccommodation(accommodation);
        newOrder.setOrderAccommodation(orderAccommodation);
        newOrder.setCategoryRoom(request.getCategory());

        return orderAccommodationDetailRepository.save(newOrder);
    }

    @Override
    public CustomPage<OrderAccommodationDetail> getAll(Pageable pageable, Integer price, Integer quantity, String categoryRoom, Long accommodationId) {
        Specification<OrderAccommodationDetail> specification = OrderAccommodationDetailSpecification.getSpecification(price, quantity, categoryRoom, accommodationId);
        var orderAccommodationDetailPage = orderAccommodationDetailRepository.findAll(specification, pageable);
        return new CustomPage<>(orderAccommodationDetailPage);
    }

    @Override
    public OrderAccommodationDetail getOne(Long id) {
        return orderAccommodationDetailRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
    }
}
