package enigma.travelwise.utils.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomDestinationResponse {
    private Long id;
    private String name;
    private String description;
    private String location;
    private String category;
    private Map<String, Integer> category_prices;
    private Map<LocalDate, WeatherData.ListItem> weather;
    private Map<String, Double> coordinates;
    private Map<String, String> pictures;
}
