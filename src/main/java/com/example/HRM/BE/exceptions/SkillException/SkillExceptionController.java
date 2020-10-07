package com.example.HRM.BE.exceptions.SkillException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SkillExceptionController {

    @ExceptionHandler(value = SkillNotFound.class)
    public ResponseEntity<Object> exception(SkillNotFound exception) {
        return new ResponseEntity<>("Skill is not found",HttpStatus.NOT_FOUND);
    }
}
