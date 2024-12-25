package nflx.rozhnov.accountservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
public class Transaction {
    @Id
    private UUID id;
    private String status;
    private String message;
    private ZonedDateTime timestamp;
    private Long fromAccount;
    private Long toAccount;
    private Double amount;
}
