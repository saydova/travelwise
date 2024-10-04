package enigma.travelwise.service.impl;

import enigma.travelwise.service.WeatherService;
import enigma.travelwise.utils.dto.WeatherData;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {
    private final RestClient restClient;
    private final String apiUrl = "http://api.openweathermap.org";

    @Value("${openwheater.api.key}")
    private String apiKey;

    @Override
    public WeatherData getWeather(Double latitude, Double longitude) {
        return restClient.get().uri(apiUrl + "/data/2.5/forecast?lat=" + latitude + "&lon=" + longitude + "&appid=" + apiKey)
                .accept(MediaType.APPLICATION_NDJSON)
                .retrieve().body(WeatherData.class);
    }
}
