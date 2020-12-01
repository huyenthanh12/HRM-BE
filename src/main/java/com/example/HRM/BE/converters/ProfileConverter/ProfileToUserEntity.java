package com.example.HRM.BE.converters.ProfileConverter;

import com.example.HRM.BE.DTO.Profile;
import com.example.HRM.BE.DTO.Skill;
import com.example.HRM.BE.converters.Bases.Converter;
import com.example.HRM.BE.entities.RoleEntity;
import com.example.HRM.BE.entities.SkillEntity;
import com.example.HRM.BE.entities.UserEntity;
import com.example.HRM.BE.repositories.SkillRepository;
import com.example.HRM.BE.services.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class ProfileToUserEntity extends Converter<Profile, UserEntity> {

    @Autowired
    private Converter<Skill, SkillEntity> skillToSkillEntity;

    @Autowired
    SkillRepository skillRepository;

    @Autowired
    SkillService skillService;

    @Override
    public UserEntity convert(Profile source) {
        UserEntity userEntity = new UserEntity();

        userEntity.setId(source.getId());
        userEntity.setEmail(source.getEmail());
        userEntity.setAddress(source.getAddress());
        userEntity.setAge(source.getAge());
        userEntity.setBirthday(source.getBirthday());
        userEntity.setFirstName(source.getFirstName());
        userEntity.setLastName(source.getLastName());
        userEntity.setSex(source.getSex());
        userEntity.setStartingDay(source.getStartingDate());

        Set<SkillEntity> listSkills = new HashSet<>();
        if (source.getSkills() != null) {
            for (Skill skill : source.getSkills()) {
                SkillEntity skillEntity = new SkillEntity();

                if (skill.getId() != 0) {
                    skillEntity = this.skillService.getSkillEntityFollowId(skill.getId());
                } else {
                    skillEntity = this.skillService.getSkillEntityFollowName(skill.getName());
                }
                listSkills.add(skillEntity);
            }
        }
        userEntity.setSkillEntities(listSkills);

        return userEntity;
    }
}
