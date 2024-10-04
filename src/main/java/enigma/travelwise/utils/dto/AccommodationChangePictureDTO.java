package enigma.travelwise.utils.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationChangePictureDTO {
    @NotNull
    private List<MultipartFile> pictures;
}
