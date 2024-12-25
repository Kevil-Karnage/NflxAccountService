package nflx.rozhnov.accountservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class AccountPutBalanceRs {
    private UUID transactionId;
    private String status;
    private String message;
    private Double newBalance;
    private ZonedDateTime timestamp;
}
