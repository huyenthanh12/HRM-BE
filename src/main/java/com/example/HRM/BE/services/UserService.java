package com.example.HRM.BE.services;

import com.example.HRM.BE.DTO.Profile;
import com.example.HRM.BE.converters.Bases.Converter;
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

    public void addNewUser(Profile profile) {
        profileService.addNewProfile(profile);
    }

    public void deleteUserFollowId(int id) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(id);

        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            userRepository.delete(userEntity);
        }
        throw new UserNotFoundException();
    }
}
