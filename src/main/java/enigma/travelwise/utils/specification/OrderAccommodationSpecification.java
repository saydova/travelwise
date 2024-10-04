package enigma.travelwise.utils.specification;

import enigma.travelwise.model.Accommodation;
import enigma.travelwise.model.OrderAccommodation;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderAccommodationSpecification {

    public static Specification<OrderAccommodation> getSpecification(
            Long userId, Integer totalPrice, LocalDate checkIn, LocalDate checkOut) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (userId != null) {
                predicates.add(criteriaBuilder.equal(root.get("user").get("id"), userId));
            }

            if (totalPrice != null) {
                predicates.add(criteriaBuilder.equal(root.get("totalPrice"), totalPrice));
            }

            if (checkIn != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("checkIn"), checkIn));
            }

            if (checkOut != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("checkOut"), checkOut));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
