package nflx.rozhnov.accountservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
public class AccountAddBalanceRs {
    private UUID transactionId;
    private BigDecimal newBalance;
    private Date timestamp;
}
