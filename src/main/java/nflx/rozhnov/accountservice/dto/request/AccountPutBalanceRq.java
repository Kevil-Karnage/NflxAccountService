package nflx.rozhnov.accountservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountPutBalanceRq {
    private Double amount;
}
