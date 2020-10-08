package com.example.HRM.BE.exceptions.RequestTypeException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RequestTypeExceptionController {

    @ExceptionHandler(value = RequestTypeNotFound.class)
    public ResponseEntity<Object> exception(RequestTypeNotFound exception) {
        return new ResponseEntity<>("Request type is not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = RequestTypeHasExisted.class)
    public ResponseEntity<Object> exception(RequestTypeHasExisted exception) {
        return new ResponseEntity<>("Request Type has existed in database", HttpStatus.FORBIDDEN);
    }
}
