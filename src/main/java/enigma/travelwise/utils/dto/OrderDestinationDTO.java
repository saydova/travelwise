package enigma.travelwise.utils.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderDestinationDTO {
    private Long userId;
    private List<OrderDestinationDetailDTO> orderDestinationDetails;
    private LocalDate orderDate;
}
