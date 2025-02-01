package nflx.rozhnov.accountservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
public class AccountGetBalanceRs {
    private long accountId;
    private BigDecimal balance;
    private Date timestamp;
}
