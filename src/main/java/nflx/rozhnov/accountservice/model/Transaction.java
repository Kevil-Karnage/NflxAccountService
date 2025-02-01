package nflx.rozhnov.accountservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
public class Transaction {
    @Id
    private UUID id;
    private Date timestamp;
    private Long fromAccount; // null, если пополнение аккаунта извне (не с другого аккаунта)
    private Long toAccount;
    private BigDecimal amount;
}
