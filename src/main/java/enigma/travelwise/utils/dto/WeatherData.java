package enigma.travelwise.utils.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeatherData {

    @JsonProperty("list")
    private List<ListItem> list;

    @Setter
    @Getter
    public static class ListItem {
        // Getters and Setters
        @JsonProperty("dt")
        private long dt;

        @JsonProperty("main")
        private Main main;

        @JsonProperty("weather")
        private List<Weather> weather;

        @JsonProperty("dt_txt")
        private String dtTxt;

    }

    @Setter
    @Getter
    public static class Main {
        // Getters and Setters
        @JsonProperty("temp")
        private double temp;

        @JsonProperty("temp_min")
        private double tempMin;

        @JsonProperty("temp_max")
        private double tempMax;

        @JsonProperty("pressure")
        private int pressure;

        @JsonProperty("sea_level")
        private int seaLevel;

        @JsonProperty("temp_kf")
        private double tempKf;

    }

    @Setter
    @Getter
    public static class Weather {
        // Getters and Setters
        @JsonProperty("id")
        private int id;

        @JsonProperty("main")
        private String main;

        @JsonProperty("description")
        private String description;

        @JsonProperty("icon")
        private String icon;

    }
}

