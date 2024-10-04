package enigma.travelwise.utils.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class DestionationDTO {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotBlank
    private String locations;
    @NotBlank
    private String categories;

//    private Map<String, MultipartFile> pictures;
    private Double latitude;
    private Double longitude;
    private Map<String, Integer> category_prices;

}
