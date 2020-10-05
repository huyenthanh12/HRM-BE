package com.example.HRM.BE.exceptions.CategoryException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CategoryExceptionController {

    @ExceptionHandler(value = CategoryNotFound.class)
    public ResponseEntity<Object> exception(CategoryNotFound exception) {
        return new ResponseEntity<>("Category is not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = CategoryHasExisted.class)
    public ResponseEntity<Object> exception(CategoryHasExisted exception) {
        return new ResponseEntity<>("Category has existed", HttpStatus.FORBIDDEN);
    }
}
