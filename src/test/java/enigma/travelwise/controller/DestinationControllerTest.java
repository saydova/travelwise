package enigma.travelwise.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import enigma.travelwise.service.DestinationService;
import enigma.travelwise.utils.dto.CustomPage;
import enigma.travelwise.utils.dto.DestionationDTO;
import enigma.travelwise.model.Destination;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DestinationControllerTest {

    @Mock
    private DestinationService destinationService;

    @InjectMocks
    private DestinationController destinationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() throws Exception {
        DestionationDTO dto = new DestionationDTO();
        Destination destination = new Destination();
        when(destinationService.create(dto)).thenReturn(destination);

        ResponseEntity<?> response = destinationController.create(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        // Add more assertions based on your Response class structure
    }
}