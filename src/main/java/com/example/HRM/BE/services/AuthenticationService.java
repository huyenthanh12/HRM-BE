package com.example.HRM.BE.services;

import com.example.HRM.BE.DTO.Token;
import com.example.HRM.BE.DTO.User;
import com.example.HRM.BE.configurations.TokenProvider;
import com.example.HRM.BE.entities.UserEntity;
import com.example.HRM.BE.exceptions.UserException.UserDisableException;
import com.example.HRM.BE.repositories.RoleRepository;
import com.example.HRM.BE.repositories.UserRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class AuthenticationService {

    private static final JacksonFactory jsonFactory = new JacksonFactory();

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private TokenProvider jwtTokeUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    public String getEmailFromTokenUser(String tokenGoogle) throws IOException {

        GoogleIdToken idToken = GoogleIdToken.parse(jsonFactory, tokenGoogle);
        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();

            String email = payload.getEmail();

            checkForUserRegister(email,
                    (String) payload.get("family_name"),
                    (String) payload.get("given_name"));

            checkForUserIsDisable(email);

            return email;
        }
        return null;
    }

    public ResponseEntity<Token> generateToken(String email, String pass) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email,
                        pass
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokeUtil.generationToken(authentication);
        return ResponseEntity.ok(new Token(token));
    }

    public void checkForUserIsDisable(String email) {
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);
        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            if (!userEntity.isEnable()) {
                throw new UserDisableException();
            }
        }
    }

    private void checkForUserRegister(String email, String family_name, String given_name) {

    }

    public void saveNewAccount(String email, String firstName, String lastName) {

        UserEntity userEntity = new UserEntity();

    }

    public ResponseEntity<Token> loginWithUsernamePassword(User user) {

//        // doan ni coi co username ko, ko thi thở exception luon
//        UserEntity userFromDatabase = userRepository.findByEmail(user.getUsername()).orElseThrow(
//                () -> new UsernameNotFoundException(user.getUsername())
//        );
//
//        System.out.println("username dung");
//        // eo co exception cho pasword // de copy qua // o deo c
//
//        if (!BCrypt.checkpw(user.getPassword(), userFromDatabase.getPassword())) {
//            System.out.println("pass sai");
//            System.out.printf(new BCryptPasswordEncoder().encode(user.getPassword()));
//            System.out.println("user dât: " + userFromDatabase.getPassword());
//
//            throw new UsernameNotFoundException(user.getUsername());
//        }
//        System.out.println("username + pass deu dung");
        return generateToken(user.getUsername(), user.getPassword());
    }
}
