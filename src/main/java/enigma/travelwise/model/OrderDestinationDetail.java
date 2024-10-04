package enigma.travelwise.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_destination_details")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderDestinationDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer price;

    private Integer quantity;

    private String categoryTicket;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private OrderDestination orderDestination;

    @ManyToOne
    @JoinColumn(name = "destination_id")
    private Destination destination;
}
