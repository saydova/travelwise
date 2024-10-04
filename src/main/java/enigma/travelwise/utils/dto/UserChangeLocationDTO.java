package enigma.travelwise.utils.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserChangeLocationDTO {
    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;
}
