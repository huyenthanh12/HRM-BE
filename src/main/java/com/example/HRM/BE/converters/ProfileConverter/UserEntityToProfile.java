package com.example.HRM.BE.converters.ProfileConverter;

import com.example.HRM.BE.DTO.Profile;
import com.example.HRM.BE.DTO.Skill;
import com.example.HRM.BE.converters.Bases.Converter;
import com.example.HRM.BE.entities.RoleEntity;
import com.example.HRM.BE.entities.SkillEntity;
import com.example.HRM.BE.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Component
public class UserEntityToProfile extends Converter<UserEntity, Profile> {

    @Autowired
    private Converter<SkillEntity, Skill> skillEntityToSkill;

    @Override
    public Profile convert(UserEntity source) {
        Profile profile = new Profile();

        profile.setId(source.getId());
        profile.setAddress(source.getAddress());
        profile.setAge(source.getAge());
        profile.setBirthday(source.getBirthday());
        profile.setEmail(source.getEmail());
        profile.setFirstName(source.getFirstName());
        profile.setLastName(source.getLastName());
        profile.setSex(source.getSex());
        profile.setStartingDate(source.getStartingDay());
        profile.setPhone(source.getPhone());

        List<Skill> listSkills = new ArrayList<>();
        for (SkillEntity skillEntity : source.getSkillEntities()) {
            listSkills.add(skillEntityToSkill.convert(skillEntity));
        }
        profile.setSkills(listSkills);

        if (source.getAvatar() != null) {
            profile.setAvatarBase(new String(Base64.getEncoder().encode(source.getAvatar())));
        }
        profile.setDisable(source.isDisable());

        List<Profile.Role> rolesResult = new ArrayList<>();
        for (RoleEntity roleEntity : source.getRoleEntities()) {
            rolesResult.add(
                    Profile.Role.builder().name(roleEntity.getName()).build()
            );
        }
        profile.setRoles(rolesResult);

        return profile;
    }
}
