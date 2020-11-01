package com.example.HRM.BE.services;

import com.example.HRM.BE.DTO.Profile;
import com.example.HRM.BE.converters.Bases.Converter;
import com.example.HRM.BE.entities.RoleEntity;
import com.example.HRM.BE.entities.UserEntity;
import com.example.HRM.BE.exceptions.UserException.EmailUserIsNotMatch;
import com.example.HRM.BE.exceptions.UserException.UserHasExisted;
import com.example.HRM.BE.exceptions.UserException.UserNotFoundException;
import com.example.HRM.BE.repositories.RoleRepository;
import com.example.HRM.BE.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProfileService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private Converter<UserEntity, Profile> userEntityToProfile;

    @Autowired
    private Converter<Profile, UserEntity> profileToUserEntity;

    public Profile getProfile(String emailUser) {
        if (userRepository.findByEmail(emailUser) == null) {
            throw new UserNotFoundException();
        }
        return userEntityToProfile.convert(userRepository.findByEmail(emailUser).get());
    }

    public void editProfile(Profile profile) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(profile.getId());
        if (!userEntityOptional.isPresent()) {
            throw new UserNotFoundException();
        }
        UserEntity userEntity = userEntityOptional.get();

        //check for email from client is exactly
        if (!userEntity.getEmail().equals(profile.getEmail())) {
            throw new EmailUserIsNotMatch();
        }
        UserEntity resultUserEntity = profileToUserEntity.convert(profile);

        //add password default
        resultUserEntity.setPassword(userEntity.getPassword());

        //add avatar default
        resultUserEntity.setAvatar(userEntity.getAvatar());

        //add role default
        resultUserEntity.setRoleEntities(userEntity.getRoleEntities());

        userRepository.save(resultUserEntity);
    }

    public void uploadAvatar(byte[] avatarBase, int idUser) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(idUser);
        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            userEntity.setAvatar(avatarBase);
            userRepository.save(userEntity);
        } else {
            throw new UserNotFoundException();
        }
    }

    public Profile getProfileFollowIdUser(int idUser) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(idUser);
        if (userEntityOptional.isPresent()) {
            return userEntityToProfile.convert(userEntityOptional.get());
        }
        throw new UserNotFoundException();
    }

    public List<Profile> searchAllUserFollowKeyword(String keyword) {
        List<Profile> profiles = new ArrayList<>();

        for (UserEntity userEntity : userRepository.findAllUserByKeyWord(keyword)) {
            profiles.add(userEntityToProfile.convert(userEntity));
        }
        return profiles;
    }

    public void addNewProfile(Profile profile) {
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(profile.getEmail());

        if (userEntityOptional.isPresent() && userEntityOptional.get().isDisable()) {
            throw new UserHasExisted();
        }
        UserEntity userEntity = profileToUserEntity.convert(profile);
        userEntity.setPassword(new BCryptPasswordEncoder().encode(userEntity.getEmail()));
        //set role default
        Set<RoleEntity> roleEntities = new HashSet<>();
        roleEntities.add(roleRepository.findByName("ROLE_MEMBER").get());
        userEntity.setRoleEntities(roleEntities);

        userRepository.save(userEntity);
    }
}
