package enigma.travelwise.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import enigma.travelwise.model.Accommodation;
import enigma.travelwise.repository.AccommodationRepository;
import enigma.travelwise.service.impl.AccommodationServiceImpl;
import enigma.travelwise.utils.dto.AccommodationDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class AccommodationServiceImplTest {

    @Mock
    private CloudinaryService cloudinaryService;

    @Mock
    private AccommodationRepository accommodationRepository;

    @InjectMocks
    private AccommodationServiceImpl accommodationService;

    private AccommodationDTO accommodationDTO;
    private Accommodation accommodation;
    private List<MultipartFile> files;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        accommodationDTO = new AccommodationDTO();
        accommodationDTO.setName("Luxury Suite");
        accommodationDTO.setDescription("A luxurious suite with a sea view");
        accommodationDTO.setLocation("Malibu");
        accommodationDTO.setCategory("Suite");
        accommodationDTO.setCategory_prices(Map.of("standard", 200));
        accommodationDTO.setLatitude(34.0207);
        accommodationDTO.setLongitude(-118.4919);

        accommodation = new Accommodation();
        accommodation.setId(1L);
        accommodation.setName("Luxury Suite");
        accommodation.setDescription("A luxurious suite with a sea view");
        accommodation.setLocation("Malibu");
        accommodation.setCategory("Suite");
        accommodation.setCategoryPrices(Map.of("standard", 200));
        accommodation.setLatitude(34.0207);
        accommodation.setLongitude(-118.4919);

        files = new ArrayList<>();

        MultipartFile file = new MockMultipartFile("images", "test.jpg", "image/jpeg", "test image content".getBytes());
        files = new ArrayList<>();
        files.add(file);
    }

    @Test
    void testCreate() {
        when(accommodationRepository.save(any(Accommodation.class))).thenReturn(accommodation);

        Accommodation createdAccommodation = accommodationService.create(accommodationDTO);

        assertEquals("Luxury Suite", createdAccommodation.getName());
        assertEquals("A luxurious suite with a sea view", createdAccommodation.getDescription());
        verify(accommodationRepository, times(1)).save(any(Accommodation.class));
    }

    @Test
    void testUpdatePhoto() {
        when(accommodationRepository.findById(anyLong())).thenReturn(Optional.of(accommodation));
        when(cloudinaryService.uploadFile(any(MultipartFile.class), eq("travelwise_accommodation")))
                .thenReturn("https://res.cloudinary.com/image.jpg");
        when(accommodationRepository.save(any(Accommodation.class))).thenAnswer(invocation -> {
            Accommodation savedAccommodation = invocation.getArgument(0);
            savedAccommodation.setPictures(Map.of("pict_0", "https://res.cloudinary.com/image.jpg"));
            return savedAccommodation;
        });

        Accommodation updatedAccommodation = accommodationService.updatePhoto(files, 1L);

        assertNotNull(updatedAccommodation.getPictures());
        assertFalse(updatedAccommodation.getPictures().isEmpty());

        verify(cloudinaryService, times(1)).uploadFile(any(MultipartFile.class), eq("travelwise_accommodation"));
        verify(accommodationRepository, times(1)).save(any(Accommodation.class));
    }

    @Test
    void testUpdate() {
        when(accommodationRepository.findById(anyLong())).thenReturn(Optional.of(accommodation));
        when(accommodationRepository.save(any(Accommodation.class))).thenReturn(accommodation);

        Accommodation updatedAccommodation = accommodationService.update(accommodationDTO, 1L);

        assertEquals("Luxury Suite", updatedAccommodation.getName());
        assertEquals("A luxurious suite with a sea view", updatedAccommodation.getDescription());
        verify(accommodationRepository, times(1)).save(any(Accommodation.class));
    }

    @Test
    void testDeleteById() {
        when(accommodationRepository.findById(anyLong())).thenReturn(Optional.of(accommodation));
        doNothing().when(accommodationRepository).delete(any(Accommodation.class));

        String result = accommodationService.deleteById(1L);

        assertEquals("Accommodation with id 1 deleted", result);
        verify(accommodationRepository, times(1)).delete(any(Accommodation.class));
    }
}

