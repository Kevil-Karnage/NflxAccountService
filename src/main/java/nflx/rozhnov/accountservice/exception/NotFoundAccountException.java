package nflx.rozhnov.accountservice.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NotFoundAccountException extends RuntimeException {
  public NotFoundAccountException() {
    super("Not found that account");
    log.info("---| Failed |---");
    log.info("Caused by {}", this.getMessage());
  }
}
