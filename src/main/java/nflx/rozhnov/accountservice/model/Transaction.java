package nflx.rozhnov.accountservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

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
    private long fromAccount; // null, если пополнение аккаунта извне (не с другого аккаунта)
    private long toAccount;
    private double amount;
}
