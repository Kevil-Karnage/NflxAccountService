package nflx.rozhnov.accountservice.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TransactionNotSavedException extends RuntimeException {
    public TransactionNotSavedException(String msg) {
        super("Error saving the transaction");
        log.error("---| Failed |---");
        log.error("Caused by {}", this.getMessage());
    }
}
