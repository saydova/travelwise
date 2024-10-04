package enigma.travelwise.service;

import enigma.travelwise.model.OrderAccommodation;
import enigma.travelwise.model.PaymentStatus;
import enigma.travelwise.model.UserEntity;
import enigma.travelwise.repository.OrderAccommodationRepository;
import enigma.travelwise.service.impl.OrderAccommodationServiceImpl;
import enigma.travelwise.utils.dto.CustomPage;
import enigma.travelwise.utils.dto.OrderAccommodationDTO;
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

class OrderAccommodationServiceImplTest {

    @Mock
    private OrderAccommodationRepository orderAccommodationRepository;

    @Mock
    private OrderAccommodationDetailService orderAccommodationDetailService;

    @Mock
    private UserService userService;

    @Mock
    private AccommodationService accommodationService;

    @InjectMocks
    private OrderAccommodationServiceImpl orderAccommodationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        // Arrange
        OrderAccommodationDTO request = new OrderAccommodationDTO();
        request.setUserId(1L);
        request.setCheckIn(LocalDate.now());
        request.setCheckOut(LocalDate.now().plusDays(2));
        request.setOrderAccommodationDetails(Collections.emptyList());

        UserEntity user = new UserEntity();
        when(userService.getById(1L)).thenReturn(user);

        OrderAccommodation savedOrder = new OrderAccommodation();
        savedOrder.setId(UUID.randomUUID());
        when(orderAccommodationRepository.save(any(OrderAccommodation.class))).thenReturn(savedOrder);

        // Act
        OrderAccommodation result = orderAccommodationService.create(request);

        // Assert
        assertNotNull(result);
        assertEquals(savedOrder.getId(), result.getId());
        assertEquals(PaymentStatus.PROCESSING, result.getStatus());
        verify(orderAccommodationRepository, times(2)).save(any(OrderAccommodation.class));
    }

//    @Test
//    void testGetAll() {
//        // Arrange
//        Pageable pageable = Pageable.unpaged();
//        Page<OrderAccommodation> page = new PageImpl<>(Collections.emptyList());
//        when(orderAccommodationRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);
//
//        // Act
//        CustomPage<OrderAccommodation> result = orderAccommodationService.getAll(pageable, null, null, null, null);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(page.getContent(), result.getContent());
//        verify(orderAccommodationRepository).findAll(any(Specification.class), eq(pageable));
//    }

    @Test
    void testGetOne() {
        // Arrange
        UUID id = UUID.randomUUID();
        OrderAccommodation order = new OrderAccommodation();
        when(orderAccommodationRepository.findById(id)).thenReturn(Optional.of(order));

        // Act
        OrderAccommodation result = orderAccommodationService.getOne(id.toString());

        // Assert
        assertNotNull(result);
        assertEquals(order, result);
    }

    @Test
    void testUpdatePaymentStatus() {
        // Arrange
        UUID id = UUID.randomUUID();
        OrderAccommodation order = new OrderAccommodation();
        when(orderAccommodationRepository.findById(id)).thenReturn(Optional.of(order));

        // Act
        orderAccommodationService.updatePaymentStatus(id.toString(), PaymentStatus.COMPLETED);

        // Assert
        assertEquals(PaymentStatus.COMPLETED, order.getStatus());
        verify(orderAccommodationRepository).save(order);
    }
}