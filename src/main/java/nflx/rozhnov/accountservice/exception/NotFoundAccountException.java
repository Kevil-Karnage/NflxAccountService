package nflx.rozhnov.accountservice.exception;

public class NotFoundAccountException extends RuntimeException {
  public NotFoundAccountException() {
    super("Not found that account");
  }
}
