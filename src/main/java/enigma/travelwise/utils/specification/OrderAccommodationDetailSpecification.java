package enigma.travelwise.utils.specification;

import enigma.travelwise.model.OrderAccommodation;
import enigma.travelwise.model.OrderAccommodationDetail;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderAccommodationDetailSpecification {

    public static Specification<OrderAccommodationDetail> getSpecification(
            Integer price, Integer quantity, String categoryRoom, Long accommodationId) {
        return (root, query, criteriaBuilder) -> {
            List<jakarta.persistence.criteria.Predicate> predicates = new ArrayList<>();

            if (price != null) {
                predicates.add(criteriaBuilder.equal(root.get("price"), price));
            }

            if (quantity != null) {
                predicates.add(criteriaBuilder.equal(root.get("quantity"), quantity));
            }

            if (categoryRoom != null && !categoryRoom.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("categoryRoom"), "%" + categoryRoom + "%"));
            }

            if (accommodationId != null) {
                predicates.add(criteriaBuilder.equal(root.get("accommodation").get("id"), accommodationId));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
