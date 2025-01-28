package nflx.rozhnov.accountservice.exception;

public class TransactionNotSavedException extends RuntimeException {
    public TransactionNotSavedException(String msg) {
        super("Ошибка сохранения транзакции: " + msg);
    }
}
