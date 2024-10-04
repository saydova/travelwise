package enigma.travelwise.repository;

import enigma.travelwise.model.OrderAccommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderAccommodationRepository extends JpaRepository<OrderAccommodation, UUID>, JpaSpecificationExecutor<OrderAccommodation> {
}
