package nflx.rozhnov.accountservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class AccountGetBalanceRs {
    private Long accountId;
    private Double balance;
    private ZonedDateTime timestamp;
}
