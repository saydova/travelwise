package enigma.travelwise.repository;

import enigma.travelwise.model.OrderDestinationDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDestinationDetailRepository extends JpaRepository<OrderDestinationDetail, Long>, JpaSpecificationExecutor<OrderDestinationDetail> {
}
