package enigma.travelwise.utils.specification;

import enigma.travelwise.model.OrderAccommodation;
import enigma.travelwise.model.OrderDestination;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderDestinationSpecification {
    public static Specification<OrderDestination> getSpecification(
            Long userId, Integer totalPrice, LocalDate orderDate) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (userId != null) {
                predicates.add(criteriaBuilder.equal(root.get("user").get("id"), userId));
            }

            if (totalPrice != null) {
                predicates.add(criteriaBuilder.equal(root.get("totalPrice"), totalPrice));
            }

            if (orderDate != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("orderDate"), orderDate));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
