package nflx.rozhnov.accountservice.dto.exception;

public class NotFoundAccountException extends RuntimeException {
  public NotFoundAccountException() {
    super("Not found that account");
  }
}
