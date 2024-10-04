package enigma.travelwise.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import enigma.travelwise.model.Destination;
import enigma.travelwise.utils.dto.CustomDestinationResponse;
import enigma.travelwise.utils.dto.CustomPage;
import enigma.travelwise.utils.dto.DestionationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DestinationService {
    Destination create(DestionationDTO req) throws JsonProcessingException;
    Destination uploadPhoto(List<MultipartFile> files, Long id);
    CustomPage<Destination> getAll(Pageable pageable, String name, String location, String category);
    CustomPage<CustomDestinationResponse> getAllWithWeather(Pageable pageable, String name, String location, String category);
    Destination getById(Long id);
    CustomDestinationResponse getWithWeatherById(Long id);

    Destination update(Long id, DestionationDTO req);
    void delete(Long id);
}
