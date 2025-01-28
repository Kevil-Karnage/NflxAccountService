package nflx.rozhnov.accountservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class AccountAddBalanceRs {
    private UUID transactionId;
    private double newBalance;
    private ZonedDateTime timestamp;
}
