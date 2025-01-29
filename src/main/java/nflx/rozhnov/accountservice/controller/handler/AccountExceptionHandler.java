package nflx.rozhnov.accountservice.controller.handler;

import nflx.rozhnov.accountservice.exception.NotFoundAccountException;
import nflx.rozhnov.accountservice.dto.response.ExceptionResponse;
import nflx.rozhnov.accountservice.exception.TransactionNotSavedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AccountExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NotFoundAccountException.class)
    private ResponseEntity<Object> handleNotFoundException(RuntimeException ex, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(404, ex.getMessage());
        return handleExceptionInternal(ex, response,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(TransactionNotSavedException.class)
    private ResponseEntity<Object> handleTransactionNotSavedException(RuntimeException ex, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(500, ex.getMessage());
        return handleExceptionInternal(ex, response,
                new HttpHeaders(), HttpStatus.valueOf(500), request);
    }
}
