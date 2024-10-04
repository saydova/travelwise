package enigma.travelwise.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "order_accommodation")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderAccommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Integer totalPrice;

    private LocalDate checkIn;

    private LocalDate checkOut;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "orderAccommodation")
    private List<OrderAccommodationDetail> accommodationDetails = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
}
