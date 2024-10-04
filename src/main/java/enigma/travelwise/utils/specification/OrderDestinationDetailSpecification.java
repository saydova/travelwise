package enigma.travelwise.utils.specification;

import enigma.travelwise.model.OrderAccommodationDetail;
import enigma.travelwise.model.OrderDestinationDetail;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class OrderDestinationDetailSpecification {
    public static Specification<OrderDestinationDetail> getSpecification(
            Integer price, Integer quantity, String categoryTicket, Long destinationId) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (price != null) {
                predicates.add(criteriaBuilder.equal(root.get("price"), price));
            }

            if (quantity != null) {
                predicates.add(criteriaBuilder.equal(root.get("quantity"), quantity));
            }

            if (categoryTicket != null && !categoryTicket.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("categoryTicket"), "%" + categoryTicket + "%"));
            }

            if (destinationId != null) {
                predicates.add(criteriaBuilder.equal(root.get("destination").get("id"), destinationId));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
