package enigma.travelwise.service;

import enigma.travelwise.utils.dto.WeatherData;

public interface WeatherService {
    WeatherData getWeather(Double latitude, Double longitude);
}
