package enigma.travelwise.utils.dto;

import enigma.travelwise.model.OrderAccommodation;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderAccommodationDetailDTO {
    private String category;
    private Integer quantity;
    private Long accommodationId;
    private OrderAccommodation orderAccommodation;
}
