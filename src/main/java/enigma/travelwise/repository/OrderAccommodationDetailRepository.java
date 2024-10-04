package enigma.travelwise.repository;

import enigma.travelwise.model.OrderAccommodation;
import enigma.travelwise.model.OrderAccommodationDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderAccommodationDetailRepository extends JpaRepository<OrderAccommodationDetail, Long>, JpaSpecificationExecutor<OrderAccommodationDetail> {
}
