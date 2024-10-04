package enigma.travelwise.service;

import enigma.travelwise.service.impl.DestinationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import enigma.travelwise.model.Destination;
import enigma.travelwise.repository.DestinationRepository;
import enigma.travelwise.service.CloudinaryService;
import enigma.travelwise.service.WeatherService;
import enigma.travelwise.utils.dto.CustomPage;
import enigma.travelwise.utils.dto.DestionationDTO;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DestinationServiceImplTest {

    @Mock
    private DestinationRepository destinationRepository;

    @Mock
    private CloudinaryService cloudinaryService;

    @Mock
    private WeatherService weatherService;

    @InjectMocks
    private DestinationServiceImpl destinationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() throws Exception {
        DestionationDTO dto = new DestionationDTO();
        dto.setName("Test Destination");
        dto.setDescription("Test Description");
        dto.setLocations("Test Location");
        dto.setCategories("Test Category");
        dto.setLatitude(1.0);
        dto.setLongitude(1.0);

        Map<String, Integer> categoryPrices = new HashMap<>();
        categoryPrices.put("category1", 100);
        dto.setCategory_prices(categoryPrices);

        Destination destination = new Destination();
        destination.setId(1L);
        destination.setName(dto.getName());

        when(destinationRepository.save(any(Destination.class))).thenReturn(destination);

        Destination result = destinationService.create(dto);

        assertNotNull(result);
        assertEquals(dto.getName(), result.getName());
        verify(destinationRepository).save(any(Destination.class));
    }

//    @Test
//    void testGetAll() {
//        Pageable pageable = mock(Pageable.class);
//        Page<Destination> page = mock(Page.class);
//        List<Destination> destinations = new ArrayList<>();
//        destinations.add(new Destination());
//
//        when(destinationRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);
//        when(page.getContent()).thenReturn(destinations);
//
//        CustomPage<Destination> result = destinationService.getAll(pageable, null, null, null);
//
//        assertNotNull(result);
//        assertFalse(result.getContent().isEmpty());
//    }
}