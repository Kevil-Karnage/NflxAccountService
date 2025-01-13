package nflx.rozhnov.accountservice.controller.handlers;

import nflx.rozhnov.accountservice.dto.exception.NotFoundAccountException;
import nflx.rozhnov.accountservice.dto.response.ExceptionResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AccountExceptionHandler {

    @ExceptionHandler(NotFoundAccountException.class)
    private ExceptionResponse handleNotFoundException() {
        return new ExceptionResponse(400, "Not found");
    }
}
