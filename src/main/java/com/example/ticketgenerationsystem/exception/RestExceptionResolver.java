package com.example.ticketgenerationsystem.exception;

import com.example.ticketgenerationsystem.response.ResponseBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionResolver extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = { ApiException.class })
    protected ResponseEntity<ResponseBean<Object>> handleKnownException(ApiException e) {
        return new ResponseEntity<>(new ResponseBean<>(false, "400", e.getMessage(), null), HttpStatus.BAD_REQUEST);
    }
}
