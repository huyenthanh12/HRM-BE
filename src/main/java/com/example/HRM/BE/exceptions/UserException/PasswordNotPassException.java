package com.example.HRM.BE.exceptions.UserException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class PasswordNotPassException extends RuntimeException{
}
