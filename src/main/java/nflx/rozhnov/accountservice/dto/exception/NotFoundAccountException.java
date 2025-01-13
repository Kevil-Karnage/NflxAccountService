package nflx.rozhnov.accountservice.dto.exception;

public class NotFoundAccountException extends RuntimeException {
  public NotFoundAccountException(String message) {
    super(message);
  }
}
