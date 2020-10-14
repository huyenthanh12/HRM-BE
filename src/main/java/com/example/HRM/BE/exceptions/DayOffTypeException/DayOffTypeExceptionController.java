package com.example.HRM.BE.exceptions.DayOffTypeException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DayOffTypeExceptionController {

    @ExceptionHandler(value = DayOffTypeNotFound.class)
    public ResponseEntity<Object> exception(DayOffTypeNotFound exception) {
        return new ResponseEntity<>("Type of day off is not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = DayOffTypeHasExisted.class)
    public ResponseEntity<Object> exception(DayOffTypeHasExisted exception) {
        return new ResponseEntity<>("Type of day off has existed in the database", HttpStatus.FORBIDDEN);
    }
}
