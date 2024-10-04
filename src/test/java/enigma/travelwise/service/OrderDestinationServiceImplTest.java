package enigma.travelwise.service;

import enigma.travelwise.model.OrderDestination;
import enigma.travelwise.model.PaymentStatus;
import enigma.travelwise.model.UserEntity;
import enigma.travelwise.repository.OrderDestinationRepository;
import enigma.travelwise.service.impl.OrderDestinationServiceImpl;
import enigma.travelwise.utils.dto.CustomPage;
import enigma.travelwise.utils.dto.OrderDestinationDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class OrderDestinationServiceImplTest {

    @Mock
    private OrderDestinationRepository orderDestinationRepository;

    @Mock
    private OrderDestinationDetailService orderDestinationDetailService;

    @Mock
    private UserService userService;

    @Mock
    private DestinationService destinationService;

    @InjectMocks
    private OrderDestinationServiceImpl orderDestinationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        // Arrange
        OrderDestinationDTO request = new OrderDestinationDTO();
        request.setUserId(1L);
        request.setOrderDate(LocalDate.now());
        request.setOrderDestinationDetails(Collections.emptyList());

        UserEntity user = new UserEntity();
        when(userService.getById(1L)).thenReturn(user);

        OrderDestination savedOrder = new OrderDestination();
        savedOrder.setId(UUID.randomUUID());
        when(orderDestinationRepository.save(any(OrderDestination.class))).thenReturn(savedOrder);

        // Act
        OrderDestination result = orderDestinationService.create(request);

        // Assert
        assertNotNull(result);
        assertEquals(savedOrder.getId(), result.getId());
        assertEquals(PaymentStatus.PROCESSING, result.getStatus());
        verify(orderDestinationRepository, times(2)).save(any(OrderDestination.class));
    }

//    @Test
//    void testGetAll() {
//        // Arrange
//        Pageable pageable = Pageable.unpaged();
//        Page<OrderDestination> page = new PageImpl<>(Collections.emptyList());
//        when(orderDestinationRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);
//
//        // Act
//        Page<OrderDestination> result = (Page<OrderDestination>) orderDestinationService.getAll(Pageable.unpaged(), null, null, null);
////        CustomPage<OrderDestination> result = orderDestinationService.getAll(pageable, null, null, null);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(page.getSize(), result.getSize());
//        verify(orderDestinationRepository).findAll(any(Specification.class), eq(pageable));
//    }

    @Test
    void testGetOne() {
        // Arrange
        UUID id = UUID.randomUUID();
        OrderDestination order = new OrderDestination();
        when(orderDestinationRepository.findById(id)).thenReturn(Optional.of(order));

        // Act
        OrderDestination result = orderDestinationService.getOne(id.toString());

        // Assert
        assertNotNull(result);
        assertEquals(order, result);
    }

    @Test
    void testUpdatePaymentStatus() {
        // Arrange
        UUID id = UUID.randomUUID();
        OrderDestination order = new OrderDestination();
        when(orderDestinationRepository.findById(id)).thenReturn(Optional.of(order));

        // Act
        orderDestinationService.updatePaymentStatus(id.toString(), PaymentStatus.COMPLETED);

        // Assert
        assertEquals(PaymentStatus.COMPLETED, order.getStatus());
        verify(orderDestinationRepository).save(order);
    }
}