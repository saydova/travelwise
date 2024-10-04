package enigma.travelwise.service.impl;

import enigma.travelwise.model.Accommodation;
import enigma.travelwise.model.UserEntity;
import enigma.travelwise.repository.AccommodationRepository;
import enigma.travelwise.service.AccommodationService;
import enigma.travelwise.service.CloudinaryService;
import enigma.travelwise.utils.dto.AccommodationDTO;
import enigma.travelwise.utils.dto.CustomPage;
import enigma.travelwise.utils.specification.AccommodationSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccommodationServiceImpl implements AccommodationService {
    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired
    private AccommodationRepository accommodationRepository;

    @Override
    public Accommodation create(AccommodationDTO request) {
        Accommodation accommodation = new Accommodation();
        accommodation.setName(request.getName());
        accommodation.setDescription(request.getDescription());
        accommodation.setLocation(request.getLocation());
        accommodation.setCategory(request.getCategory());
        accommodation.setCategoryPrices(request.getCategory_prices());
        accommodation.setLatitude(request.getLatitude());
        accommodation.setLongitude(request.getLongitude());
        return accommodationRepository.save(accommodation);
    }

    @Override
    public CustomPage<Accommodation> getAll(Pageable pageable, String name, String location, String category) {
        Specification<Accommodation> specification = AccommodationSpecification.getSpecification(name, location, category);
        Page<Accommodation> accommodationPage = accommodationRepository.findAll(specification, pageable);
        return new CustomPage<>(accommodationPage);
    }

    @Override
    public Accommodation getById(Long id) {
        return accommodationRepository.findById(id)
                .orElse(null);
//                .orElseThrow(() -> new RuntimeException("ACCOMMODATION WITH ID + " + id + " NOT FOUND")

    }

    @Override
    public Accommodation updatePhoto(List<MultipartFile> files, Long id) {
        Accommodation accommodation = this.getById(id);
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < files.size(); i++) {
            String url = cloudinaryService.uploadFile(files.get(i), "travelwise_accommodation");
            map.put("pict_" + i, url);
        }
        accommodation.setPictures(map);
        return accommodationRepository.save(accommodation);
    }

    @Override
    public Accommodation update(AccommodationDTO request, Long id) {
        Accommodation updateAccomm = this.getById(id);
        updateAccomm.setName(request.getName());
        updateAccomm.setDescription(request.getDescription());
        updateAccomm.setLocation(request.getLocation());
        updateAccomm.setCategory(request.getCategory());
        updateAccomm.setCategoryPrices(request.getCategory_prices());
        updateAccomm.setLatitude(request.getLatitude());
        updateAccomm.setLongitude(request.getLongitude());
        return accommodationRepository.save(updateAccomm);
    }

    @Override
    public String deleteById(Long id) {
        Accommodation deleteAccomm = this.getById(id);
        accommodationRepository.delete(deleteAccomm);
        String result = "Accommodation with id " + id + " deleted";
        return result;
    }
}
