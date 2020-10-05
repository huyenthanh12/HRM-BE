package com.example.HRM.BE.controllers;

import com.example.HRM.BE.DTO.Token;
import com.example.HRM.BE.DTO.User;
import com.example.HRM.BE.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<Token> loginWithUsername(@RequestBody User user){
        return authenticationService.loginWithUsernamePassword(user);
    }

    @PutMapping
    public ResponseEntity<?> loginWithGoogle(@RequestHeader("token-google") String tokenGoogle) throws IOException, GeneralSecurityException {
        String email = authenticationService.getEmailFromTokenUser(tokenGoogle);
        return authenticationService.generateToken(email, email);
    }

}
