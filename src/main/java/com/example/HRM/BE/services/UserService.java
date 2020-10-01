package com.example.HRM.BE.services;

import com.example.HRM.BE.DTO.Profile;
import com.example.HRM.BE.DTO.User;
import com.example.HRM.BE.converters.bases.Converter;
import com.example.HRM.BE.entities.UserEntity;
import com.example.HRM.BE.exceptions.UserException.UserNotFoundException;
import com.example.HRM.BE.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProfileService profileService;

    @Autowired
    private Converter<UserEntity, Profile> userEntityToProfile;

    public List<Profile> getAllUser() {
        return userEntityToProfile.convert(userRepository.findAll());
    }

    public Profile getUserFollowId(int idUser) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(idUser);
        if (userEntityOptional.isPresent()) {
            return userEntityToProfile.convert(userEntityOptional.get());
        }
        throw new UserNotFoundException();
    }

    public void editUser(Profile profile) {
        profileService.editProfile(profile);
    }
}
