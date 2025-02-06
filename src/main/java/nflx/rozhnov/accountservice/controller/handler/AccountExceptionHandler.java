package nflx.rozhnov.accountservice.controller.handler;

import nflx.rozhnov.accountservice.exception.KafkaSendingException;
import nflx.rozhnov.accountservice.exception.NotFoundAccountException;
import nflx.rozhnov.accountservice.dto.response.ExceptionResponse;
import nflx.rozhnov.accountservice.exception.TransactionNotSavedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.BAD_GATEWAY;


@ControllerAdvice
public class AccountExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NotFoundAccountException.class)
    private ResponseEntity<Object> handleNotFoundException(RuntimeException ex, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(NOT_FOUND.value(), ex.getMessage());
        return handleExceptionInternal(ex, response,
                new HttpHeaders(), NOT_FOUND, request);
    }

    @ExceptionHandler(value = {TransactionNotSavedException.class, KafkaSendingException.class})
    private ResponseEntity<Object> handleTransactionNotSavedException(RuntimeException ex, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(BAD_GATEWAY.value(), ex.getMessage());
        return handleExceptionInternal(ex, response,
                new HttpHeaders(), BAD_GATEWAY, request);
    }
}
