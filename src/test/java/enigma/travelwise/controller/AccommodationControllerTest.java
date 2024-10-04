package enigma.travelwise.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import enigma.travelwise.model.Accommodation;
import enigma.travelwise.service.AccommodationService;
import enigma.travelwise.utils.dto.AccommodationDTO;
import enigma.travelwise.utils.dto.CustomPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

@WebMvcTest(AccommodationController.class)
class AccommodationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccommodationService accommodationService;

    private ObjectMapper objectMapper;
    private AccommodationDTO accommodationDTO;
    private Accommodation accommodation;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

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
    }

    @Test
    void testCreate() throws Exception {
        when(accommodationService.create(any(AccommodationDTO.class))).thenReturn(accommodation);

        mockMvc.perform(post("/api/accommodation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accommodationDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(accommodation.getName()))
                .andExpect(jsonPath("$.description").value(accommodation.getDescription()));

        verify(accommodationService, times(1)).create(any(AccommodationDTO.class));
    }

    @Test
    void testUpdatePhoto() throws Exception {
        MockMultipartFile file = new MockMultipartFile("images", "test.jpg", "image/jpeg", "test image content".getBytes());
        when(accommodationService.updatePhoto(anyList(), anyLong())).thenReturn(accommodation);

        mockMvc.perform(multipart("/api/accommodation/1/photos")
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pictures").exists());

        verify(accommodationService, times(1)).updatePhoto(anyList(), eq(1L));
    }

    @Test
    void testGetAll() throws Exception {
        List<Accommodation> accommodations = Collections.singletonList(accommodation);
        Page<Accommodation> page = new PageImpl<>(accommodations);

        when(accommodationService.getAll(any(Pageable.class), anyString(), anyString(), anyString()))
                .thenReturn(new CustomPage<>(page));

        mockMvc.perform(get("/api/accommodations")
                        .param("page", "0")
                        .param("size", "10")
                        .param("name", "someName")
                        .param("location", "someLocation")
                        .param("category", "someCategory")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value(accommodation.getName()));

        verify(accommodationService, times(1)).getAll(any(Pageable.class), anyString(), anyString(), anyString());
    }

    @Test
    void testGetById() throws Exception {
        when(accommodationService.getById(anyLong())).thenReturn(accommodation);

        mockMvc.perform(get("/api/accommodation/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(accommodation.getName()));

        verify(accommodationService, times(1)).getById(anyLong());
    }

    @Test
    void testUpdate() throws Exception {
        when(accommodationService.update(any(AccommodationDTO.class), anyLong())).thenReturn(accommodation);

        mockMvc.perform(put("/api/accommodation/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accommodationDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(accommodation.getName()));

        verify(accommodationService, times(1)).update(any(AccommodationDTO.class), eq(1L));
    }

    @Test
    void testDelete() throws Exception {
        when(accommodationService.deleteById(anyLong())).thenReturn("Accommodation with id 1 deleted");

        mockMvc.perform(delete("/api/accommodation/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Accommodation with id 1 deleted"));

        verify(accommodationService, times(1)).deleteById(eq(1L));
    }
}
