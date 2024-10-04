package enigma.travelwise.utils.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDetailDTO {
    @JsonProperty("status_code")
    private String statusCode;
    @JsonProperty("transaction_status")
    private String transactionStatus;
}
