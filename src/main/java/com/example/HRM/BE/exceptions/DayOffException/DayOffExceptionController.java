package com.example.HRM.BE.exceptions.DayOffException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DayOffExceptionController {

    @ExceptionHandler(value = DayOffNotFound.class)
    public ResponseEntity<Object> exception(DayOffNotFound exception) {
        return new ResponseEntity<>("Day Off is not found", HttpStatus.NOT_FOUND);
    }
}
