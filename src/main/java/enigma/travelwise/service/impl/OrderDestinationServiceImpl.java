package enigma.travelwise.service.impl;

import enigma.travelwise.model.*;
import enigma.travelwise.repository.OrderDestinationRepository;
import enigma.travelwise.service.*;
import enigma.travelwise.utils.dto.CustomPage;
import enigma.travelwise.utils.dto.OrderDestinationDTO;
import enigma.travelwise.utils.dto.OrderDestinationDetailDTO;
import enigma.travelwise.utils.specification.OrderDestinationSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderDestinationServiceImpl implements OrderDestinationService {
    private final OrderDestinationRepository orderDestinationRepository;
    private final OrderDestinationDetailService orderDestinationdetailService;
    private final UserService userService;
    private final DestinationService destinationService;

    @Override
    public OrderDestination create(OrderDestinationDTO request) {
        UserEntity user = userService.getById(request.getUserId());
        List<OrderDestinationDetailDTO> details = request.getOrderDestinationDetails();
        OrderDestination newOrderDestination = new OrderDestination();
        newOrderDestination.setUser(user);
        newOrderDestination.setOrderDate(request.getOrderDate());

        OrderDestination result = orderDestinationRepository.save(newOrderDestination);
        int pricePlaceHolder = 0;
        List<OrderDestinationDetail> odd_list = new ArrayList<>();
        for (OrderDestinationDetailDTO detail : details) {
            Destination des = destinationService.getById(detail.getDestinationId());
            String price_tag = detail.getCategory().toLowerCase();
            Integer cat_price = des.getCategoryPrices().get(price_tag);

            int qty = detail.getQuantity();
            cat_price *= qty;

            detail.setOrderDestination(result);
            pricePlaceHolder += cat_price;
            OrderDestinationDetail odd = orderDestinationdetailService.create(detail);
            odd_list.add(odd);
        }

        result.setTotalPrice(pricePlaceHolder);
        result.setDestinationDetails(odd_list);
        result.setStatus(PaymentStatus.PROCESSING);
        return orderDestinationRepository.save(result);
    }

    @Override
    public CustomPage<OrderDestination> getAll(Pageable pageable, Long userId, Integer totalPrice, LocalDate orderDate) {
        Specification<OrderDestination> specification = OrderDestinationSpecification.getSpecification(userId, totalPrice, orderDate);
        var orderDestinationPage = orderDestinationRepository.findAll(specification, pageable);
        return new CustomPage<>(orderDestinationPage);
    }

    @Override
    public OrderDestination getOne(String id) {
        return orderDestinationRepository.findById(UUID.fromString(id)).orElseThrow(() -> new RuntimeException("Order Destination not found"));
    }

    @Override
    public void updatePaymentStatus(String id, PaymentStatus status) {
        OrderDestination orderDestination = getOne(id);
        orderDestination.setStatus(status);
        orderDestinationRepository.save(orderDestination);
    }
}
