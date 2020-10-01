package com.example.HRM.BE.exceptions.UserException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionController {

    @ExceptionHandler(value = UserDisableException.class)
    public ResponseEntity<Object> exception(UserDisableException exception) {
        return new ResponseEntity<>("Account user is disable", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<Object> exception(UserNotFoundException exception) {
        return new ResponseEntity<>("User not found", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = UserAccessDeniedException.class)
    public ResponseEntity<Object> exception(UserAccessDeniedException exception) {
        return new ResponseEntity<>("User access denied", HttpStatus.FORBIDDEN);
    }
}
