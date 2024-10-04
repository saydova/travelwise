package enigma.travelwise.repository;

import enigma.travelwise.model.OrderDestination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderDestinationRepository extends JpaRepository<OrderDestination, UUID> , JpaSpecificationExecutor<OrderDestination> {
}
