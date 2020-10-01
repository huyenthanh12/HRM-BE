package com.example.HRM.BE.controllers;

import com.example.HRM.BE.DTO.Token;
import com.example.HRM.BE.DTO.User;
import com.example.HRM.BE.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<Token> loginWithUsername(@RequestBody User user){
//        System.out.printf("user" + user.getUsername());
//        System.out.printf("password: " + user.getPassword());
        return authenticationService.loginWithUsernamePassword(user);
    }

}
