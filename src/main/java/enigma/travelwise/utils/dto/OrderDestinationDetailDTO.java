package enigma.travelwise.utils.dto;

import enigma.travelwise.model.OrderDestination;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderDestinationDetailDTO {
    private String category;
    private Integer quantity;
    private Long destinationId;
    private OrderDestination orderDestination;
}
