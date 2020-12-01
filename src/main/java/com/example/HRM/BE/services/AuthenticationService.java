package com.example.HRM.BE.services;

import com.example.HRM.BE.DTO.Token;
import com.example.HRM.BE.DTO.User;
import com.example.HRM.BE.configurations.TokenProvider;
import com.example.HRM.BE.entities.RoleEntity;
import com.example.HRM.BE.entities.UserEntity;
import com.example.HRM.BE.exceptions.UserException.BadRequestException;
import com.example.HRM.BE.exceptions.UserException.PasswordNotPassException;
import com.example.HRM.BE.exceptions.UserException.UserAccessDeniedException;
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
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
        System.out.println("ooooo: " + idToken);
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

    public ResponseEntity<Token> generateTokenGoogle(String email) {
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);
        if (userEntityOptional.isPresent()) {
            final String token = jwtTokeUtil.generationTokenGoogle(userEntityOptional.get());
            return ResponseEntity.ok(new Token(token));
        }
        throw new BadRequestException("User is Denied to access");
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
            System.out.println(userEntityOptional.get().getEmail());

            UserEntity userEntity = userEntityOptional.get();
            if (userEntity.isDisable()) {
                System.out.println(userEntityOptional.get().isDisable());
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
        return generateToken(user.getUsername(), user.getPassword());
    }

    public ResponseEntity<Token> editAccount(User user) {
        UserEntity userEntityFromDB = this.userRepository.findByEmail(user.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException(user.getUsername())
        );
        if (!BCrypt.checkpw(user.getPassword(), userEntityFromDB.getPassword())) {
            throw new PasswordNotPassException();
        }
        userEntityFromDB = this.userRepository.save(userEntityFromDB);
        System.out.println(user.getPasswordNew());
        userEntityFromDB.setPassword(new BCryptPasswordEncoder().encode(user.getPasswordNew()));
        userEntityFromDB = this.userRepository.save(userEntityFromDB);
        System.out.println(userEntityFromDB.getEmail());
        return generateToken(userEntityFromDB.getEmail(), user.getPasswordNew());
    }

    public  ResponseEntity<Token> add(User user) {
        UserEntity userEntity =  new UserEntity();
        userEntity.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userEntity.setEmail(user.getUsername());
        Set<RoleEntity> roleEntity = new HashSet<>();
        roleEntity.add(roleRepository.findByName("ROLE_MEMBER").get());
//        System.out.println(roleEntity.getName());
        userEntity.setRoleEntities(roleEntity);
        userEntity = this.userRepository.save(userEntity);
        return generateToken(userEntity.getEmail(), userEntity.getPassword());

    }
}
