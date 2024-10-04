package enigma.travelwise.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "order_destination")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderDestination {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Integer totalPrice;

    private LocalDate orderDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "orderDestination")
    private List<OrderDestinationDetail> destinationDetails = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
}