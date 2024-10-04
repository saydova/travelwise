package enigma.travelwise.utils.specification;

import enigma.travelwise.model.Accommodation;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class AccommodationSpecification {
    public static Specification<Accommodation> getSpecification(String name, String location, String category) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null && !name.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            }

            if (location != null && !location.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("location"), "%" + location + "%"));
            }

            if (category != null && !category.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("category"), "%" + category + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
