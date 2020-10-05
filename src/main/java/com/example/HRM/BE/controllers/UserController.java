package com.example.HRM.BE.controllers;

import com.example.HRM.BE.DTO.Profile;
import com.example.HRM.BE.DTO.User;
import com.example.HRM.BE.entities.UserEntity;
import com.example.HRM.BE.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public List<Profile> getAllUser() {
        return userService.getAllUser();
    }

    @GetMapping("/{idUser}")
    public Profile getUserFollowID(@PathVariable int idUser) {
        return userService.getUserFollowId(idUser);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/{idUser}")
    public void editUser(@RequestBody @Validated Profile profile) {
        userService.editUser(profile);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    public void addNewUser(@RequestBody Profile profile) {
        userService.addNewUser(profile);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public void deleteUserFollowID(@PathVariable("id") int id) {
        userService.deleteUserFollowId(id);
    }
}
