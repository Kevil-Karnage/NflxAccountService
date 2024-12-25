package nflx.rozhnov.accountservice.dto.enums;

import lombok.Getter;

@Getter
public enum TransactionStatus {
    PENDING("Transfer initiated successfully"),
    SUCCESS("Transfer completed");

    TransactionStatus(String message) {
        this.message = message;
    }

    private final String message;
}
