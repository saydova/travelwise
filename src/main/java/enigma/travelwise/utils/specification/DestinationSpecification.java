package enigma.travelwise.utils.specification;

import enigma.travelwise.model.Destination;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class DestinationSpecification {
    public static Specification<Destination> getSpecification(String name, String location, String category) {
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
