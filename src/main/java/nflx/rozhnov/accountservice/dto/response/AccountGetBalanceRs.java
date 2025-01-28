package nflx.rozhnov.accountservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class AccountGetBalanceRs {
    private long accountId;
    private double balance;
    private Date timestamp;
}
