package nflx.rozhnov.accountservice.exception;

public class TransactionNotSavedException extends RuntimeException {
    public TransactionNotSavedException(String msg) {
        super("Error saving the transaction: " + msg);
    }
}
