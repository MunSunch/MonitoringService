package com.munsun.monitoring_service.backend.controllers.advice;

import com.munsun.monitoring_service.backend.exceptions.AccountNotFoundException;
import com.munsun.monitoring_service.backend.exceptions.AuthenticationException;
import com.munsun.monitoring_service.commons.dto.out.ErrorDtoOut;
import com.munsun.monitoring_service.commons.exceptions.MissingKeyReadingException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.context.support.DefaultMessageSourceResolvable;

import java.time.DateTimeException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class AdviceController {
    @ExceptionHandler(value = AccountNotFoundException.class)
    public ResponseEntity<ErrorDtoOut> handlerNotFoundException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorDtoOut(e.getMessage()));
    }

    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<ErrorDtoOut> handlerAuthenticationException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorDtoOut(e.getMessage()));
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ErrorDtoOut> handlerExceptions(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDtoOut(e.getMessage()));
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDtoOut exceptionObjectValidationHandler(MethodArgumentNotValidException e) {
        var message = e.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(";"));
        return new ErrorDtoOut(message);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class,
                               MissingKeyReadingException.class,
                               DateTimeException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDtoOut exceptionArgumentValidationHandler(Exception e) {
        return new ErrorDtoOut(e.getMessage());
    }
}
