package com.example.HRM.BE.exceptions.RequestException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RequestExceptionController {

    @ExceptionHandler(value = RequestNotFound.class)
    public ResponseEntity<Object> exception(RequestNotFound exception) {
        return new ResponseEntity<>("Request is not found", HttpStatus.NOT_FOUND);
    }
}
