package enigma.travelwise.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import enigma.travelwise.model.Destination;
import enigma.travelwise.model.UserEntity;
import enigma.travelwise.repository.DestinationRepository;
import enigma.travelwise.service.CloudinaryService;
import enigma.travelwise.service.DestinationService;
import enigma.travelwise.service.WeatherService;
import enigma.travelwise.utils.dto.CustomDestinationResponse;
import enigma.travelwise.utils.dto.CustomPage;
import enigma.travelwise.utils.dto.DestionationDTO;
import enigma.travelwise.utils.dto.WeatherData;
import enigma.travelwise.utils.specification.DestinationSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DestinationServiceImpl implements DestinationService {
    private final CloudinaryService cloudinaryService;
    private final DestinationRepository destinationRepository;
    private final WeatherService weatherService;
//    private final ExecutorService executorService;

    @Override
    public Destination create(DestionationDTO req) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Destination destination = Destination.builder()
                .name(req.getName())
                .categories(req.getCategories())
                .description(req.getDescription())
                .locations(req.getLocations())
                .categoryPrices(req.getCategory_prices())
                .latitude(req.getLatitude())
                .longitude(req.getLongitude())
                .build();
//        Map<String, String> map_url = new HashMap<>();
//        for (int i = 0; i < files.size(); i++) {
//            String url = cloudinaryService.uploadFile(files.get(i), "travelwise_destinations");
//            map_url.put("pict_" + i, url);
//        }
//
//        destination.setPictures(map_url);
        return destinationRepository.save(destination);
    }

    @Override
    public Destination uploadPhoto(List<MultipartFile> files, Long id) {
        Destination destination = this.getById(id);
        Map<String, String> map_url = new HashMap<>();
        for (int i = 0; i < files.size(); i++) {
            String url = cloudinaryService.uploadFile(files.get(i), "travelwise_destinations");
            map_url.put("pict_" + i, url);
        }

        destination.setPictures(map_url);
//        return destination;
        return destinationRepository.save(destination);
    }

    @Override
    public CustomPage<Destination> getAll(Pageable pageable, String name, String location, String category) {
        Specification<Destination> specification = DestinationSpecification.getSpecification(name, location, category);
        Page<Destination> destinationPage = destinationRepository.findAll(specification, pageable);

        return new CustomPage<>(destinationPage);
    }

    @Override
    public CustomPage<CustomDestinationResponse> getAllWithWeather(Pageable pageable, String name, String location, String category) {
        Specification<Destination> specification = DestinationSpecification.getSpecification(name, location, category);
        List<CustomDestinationResponse> customDestinationResponseList = new ArrayList<>();
        Page<Destination> destinationList = destinationRepository.findAll(specification, pageable);
        for (Destination destination : destinationList) {
            Map<LocalDate, WeatherData.ListItem> listWeather = weatherService.getWeather(destination.getLatitude(), destination.getLongitude()).getList()
                    .stream().filter(items -> items.getDtTxt().contains("12:00:00"))
                    .sorted(Comparator.comparing(items -> LocalDate.parse(items.getDtTxt().split(" ")[0])))
                    .collect(Collectors.toMap(
                            items -> LocalDate.parse(items.getDtTxt().split(" ")[0]), items -> items,
                            (existing, replacement) -> existing,
                            LinkedHashMap::new
                    ));
            CustomDestinationResponse customDestinationResponse = CustomDestinationResponse
                    .builder()
                    .id(destination.getId())
                    .name(destination.getName())
                    .description(destination.getDescription())
                    .location(destination.getLocations())
                    .category(destination.getCategories())
                    .pictures(destination.getPictures())
                    .category_prices(destination.getCategoryPrices())
                    .weather(listWeather)
                    .coordinates(Map.of("latitude", destination.getLatitude(), "longitude", destination.getLongitude()))
                    .build();
            customDestinationResponseList.add(customDestinationResponse);
        }
        var customPage = new PageImpl<>(customDestinationResponseList, pageable, destinationList.getTotalElements());
        return new CustomPage<>(customPage);
    }

    @Override
    public Destination getById(Long id) {
        return destinationRepository.findById(id).orElse(null);
    }

    @Override
    public CustomDestinationResponse getWithWeatherById(Long id) {
        Destination destination = this.getById(id);
        Map<LocalDate, WeatherData.ListItem> listWeather = weatherService.getWeather(-8.409518, 115.163727).getList()
                .stream().filter(items -> items.getDtTxt().contains("12:00:00"))
                .sorted(Comparator.comparing(items -> LocalDate.parse(items.getDtTxt().split(" ")[0])))
                .collect(Collectors.toMap(
                        items -> LocalDate.parse(items.getDtTxt().split(" ")[0]), items -> items,
                        (existing, replacement) -> existing,
                        LinkedHashMap::new
                ));
        return CustomDestinationResponse.builder()
                .id(destination.getId())
                .name(destination.getName())
                .description(destination.getDescription())
                .location(destination.getLocations())
                .category(destination.getCategories())
                .pictures(destination.getPictures())
                .category_prices(destination.getCategoryPrices())
                .weather(listWeather)
                .coordinates(Map.of("latitude", destination.getLatitude(), "longitude", destination.getLongitude()))
                .build();
    }

    @Override
    public Destination update(Long id, DestionationDTO req) {
        Destination destination = this.getById(id);
        destination.setName(req.getName());
        destination.setCategories(req.getCategories());
        destination.setLocations(req.getLocations());
        destination.setDescription(req.getDescription());
        destination.setCategoryPrices(req.getCategory_prices());
        destination.setLatitude(req.getLatitude());
        destination.setLongitude(req.getLongitude());
        return destinationRepository.save(destination);
    }

    @Override
    public void delete(Long id) {
        Destination destination = this.getById(id);
        destinationRepository.delete(destination);
    }
}
