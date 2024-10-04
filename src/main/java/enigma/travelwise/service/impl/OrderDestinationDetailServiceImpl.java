package enigma.travelwise.service.impl;

import enigma.travelwise.model.Destination;
import enigma.travelwise.model.OrderDestination;
import enigma.travelwise.model.OrderDestinationDetail;
import enigma.travelwise.repository.OrderDestinationDetailRepository;
import enigma.travelwise.service.DestinationService;
import enigma.travelwise.service.OrderDestinationDetailService;
import enigma.travelwise.utils.dto.CustomPage;
import enigma.travelwise.utils.dto.OrderDestinationDetailDTO;
import enigma.travelwise.utils.specification.OrderDestinationDetailSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDestinationDetailServiceImpl implements OrderDestinationDetailService {
    private final OrderDestinationDetailRepository orderDestinationDetailRepository;
    private final DestinationService destinationService;
    @Override
    public OrderDestinationDetail create(OrderDestinationDetailDTO request) {
        OrderDestinationDetail newOrder = new OrderDestinationDetail();
        Destination destination = destinationService.getById(request.getDestinationId());
        OrderDestination orderDestination = request.getOrderDestination();


        newOrder.setQuantity(request.getQuantity());
        int priceAfterQuantity = request.getQuantity() * destination.getCategoryPrices().get(request.getCategory());
        newOrder.setPrice(priceAfterQuantity);
        newOrder.setDestination(destination);
        newOrder.setOrderDestination(orderDestination);
        newOrder.setCategoryTicket(request.getCategory());

        return orderDestinationDetailRepository.save(newOrder);
    }

    @Override
    public CustomPage<OrderDestinationDetail> getAll(Pageable pageable, Integer price, Integer quantity, String categoryTicket, Long destinationId) {
        Specification<OrderDestinationDetail> specification = OrderDestinationDetailSpecification.getSpecification(price, quantity, categoryTicket, destinationId);
        var orderDestinationDetailPage = orderDestinationDetailRepository.findAll(specification, pageable);
        return new CustomPage<>(orderDestinationDetailPage);
    }


    @Override
    public OrderDestinationDetail getOne(Long id) {
        return orderDestinationDetailRepository.findById(id).orElseThrow(() -> new RuntimeException("Order Destination Details not found"));
    }
}
