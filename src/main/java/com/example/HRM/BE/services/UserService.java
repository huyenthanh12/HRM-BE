package com.example.HRM.BE.services;

import com.example.HRM.BE.DTO.Profile;
import com.example.HRM.BE.converters.Bases.Converter;
import com.example.HRM.BE.entities.DayOffEntity;
import com.example.HRM.BE.entities.RequestEntity;
import com.example.HRM.BE.entities.UserEntity;
import com.example.HRM.BE.exceptions.UserException.UserNotFoundException;
import com.example.HRM.BE.repositories.DayOffRepository;
import com.example.HRM.BE.repositories.RequestRepository;
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

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private DayOffRepository dayOffRepository;

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

        if (!userEntityOptional.isPresent()) {
            throw new UserNotFoundException();
        }
        UserEntity userEntity = userEntityOptional.get();

        //delete requests of user
        for (RequestEntity requestEntity : requestRepository.findByUserEntityId(id)) {
            requestRepository.delete(requestEntity);
        }

        //delete day offs of user
        for (DayOffEntity dayOffEntity : dayOffRepository.findByUserEntityId(id)) {
            dayOffRepository.delete(dayOffEntity);
        }

        userRepository.delete(userEntity);
    }
}
