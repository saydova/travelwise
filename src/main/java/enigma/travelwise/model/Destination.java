package enigma.travelwise.model;
import enigma.travelwise.utils.JsonConverter;
import enigma.travelwise.utils.JsonConverterString;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import jakarta.persistence.*;

import java.util.Map;

@Entity
@Table(name = "destinations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Destination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String locations;
    private String categories;

    @JdbcTypeCode(SqlTypes.JSON)
    @Convert(converter = JsonConverterString.class)
    @Column(columnDefinition = "jsonb")
    private Map<String, String> pictures;

    private Double latitude;
    private Double longitude;

    @JdbcTypeCode(SqlTypes.JSON)
    @Convert(converter = JsonConverter.class)
    @Column(columnDefinition = "jsonb")
    private Map<String, Integer> categoryPrices;
}

