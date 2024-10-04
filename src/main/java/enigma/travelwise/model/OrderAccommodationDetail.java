package enigma.travelwise.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_accommodation_details")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderAccommodationDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer price;

    private Integer quantity;

    private String categoryRoom;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private OrderAccommodation orderAccommodation;

    @ManyToOne
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;
}
