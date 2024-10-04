package enigma.travelwise.service;

import enigma.travelwise.model.Accommodation;
import enigma.travelwise.utils.dto.AccommodationDTO;
import enigma.travelwise.utils.dto.CustomPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AccommodationService {
    Accommodation create(AccommodationDTO request);
    Accommodation updatePhoto(List<MultipartFile> files, Long id);
    CustomPage<Accommodation> getAll(Pageable pageable, String name, String location, String category);
    Accommodation getById(Long id);
    Accommodation update(AccommodationDTO request, Long id);
    String deleteById(Long id);
}
