package com.munsun.monitoring_service.backend.controllers.advice;

import com.munsun.monitoring_service.commons.dto.out.ErrorDtoOut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//@RestControllerAdvice
public class ErrorController {
//    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorDtoOut> handlerExceptions(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDtoOut(e.getMessage()));
    }
}
