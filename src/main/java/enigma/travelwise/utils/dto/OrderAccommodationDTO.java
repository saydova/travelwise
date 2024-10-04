package enigma.travelwise.utils.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderAccommodationDTO {
    private Long userId;
    private List<OrderAccommodationDetailDTO> orderAccommodationDetails;
    private LocalDate checkIn;
    private LocalDate checkOut;
}
