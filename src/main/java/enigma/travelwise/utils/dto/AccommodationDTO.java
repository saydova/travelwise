package enigma.travelwise.utils.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccommodationDTO {
//    @NotBlank
    private String name;

//    @NotBlank
    private String description;

//    @NotBlank
    private String location;

//    @NotBlank
    private String category;

    private Map<String, Integer> category_prices;

//    @NotNull
    private Double latitude;

//    @NotNull
    private Double longitude;
}
