package enigma.travelwise.utils.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RefreshTokenDTO {
    @NotBlank
    private String refreshToken;
}
