package com.example.HRM.BE.services;

import com.example.HRM.BE.DTO.Skill;
import com.example.HRM.BE.converters.Bases.Converter;
import com.example.HRM.BE.entities.CategoryEntity;
import com.example.HRM.BE.entities.SkillEntity;
import com.example.HRM.BE.entities.UserEntity;
import com.example.HRM.BE.exceptions.CategoryException.CategoryNotFound;
import com.example.HRM.BE.exceptions.SkillException.SkillNotFound;
import com.example.HRM.BE.exceptions.UserException.UserNotFoundException;
import com.example.HRM.BE.repositories.CategoryRepository;
import com.example.HRM.BE.repositories.SkillRepository;
import com.example.HRM.BE.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class SkillService {

    @Autowired
    SkillRepository skillRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private Converter<SkillEntity, Skill> skillEntityToSkill;

    @Autowired
    private Converter<Skill, SkillEntity> skillToSkillEntity;

    public List<Skill> getAllSkills() {
        List<Skill> skillList = new ArrayList<>();

        for (SkillEntity skillEntity : skillRepository.findAll()) {
            skillList.add(skillEntityToSkill.convert(skillEntity));
        }
        return skillList;
    }

    public Skill getSkillFollowID(int id) {
        if (skillRepository.findById(id).isPresent()) {
            return skillEntityToSkill.convert(skillRepository.findById(id).get());
        } else {
            throw new SkillNotFound();
        }
    }

    public List<Skill> getSkillsFollowUser(int id) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(id);
        if (!userEntityOptional.isPresent()) {
            throw new UserNotFoundException();
        } else {
            UserEntity userEntity = userEntityOptional.get();

            List<Skill> skills = new ArrayList<>();
            for (SkillEntity skillEntity : userEntity.getSkillEntities()) {
                skills.add(skillEntityToSkill.convert(skillEntity));
            }
            return skills;
        }
    }

    public List<Skill> getSkillsFollowCategory(int id) {
        Optional<CategoryEntity> categoryEntityOptional = categoryRepository.findById(id);

        if (!categoryEntityOptional.isPresent()) {
            throw new CategoryNotFound();
        } else {
            CategoryEntity categoryEntity = categoryEntityOptional.get();

            List<Skill> skills = new ArrayList<>();
            for (SkillEntity skillEntity : categoryEntity.getSkillEntities()) {
                skills.add(skillEntityToSkill.convert(skillEntity));
            }
            return skills;
        }
    }

    public void addNewSkill(Skill skill) {
        SkillEntity skillEntity = skillToSkillEntity.convert(skill);

        if (isCategoryExist(skillEntity.getCategoryEntities())) {
            skillRepository.save(skillEntity);
        } else {
            throw new CategoryNotFound();
        }
    }

    private boolean isCategoryExist(CategoryEntity categoryEntities) {

        if (categoryRepository.findById(categoryEntities.getId()).isPresent()) {

            //check name of category is exactly
            if (categoryRepository.findById(categoryEntities.getId()).get().getName().equals(categoryEntities.getName())) {
                return true;
            }
        }
        return false;
    }

    public void editSkill(int id, Skill skill) {

        if (skillRepository.findById(id).isPresent()) {
            SkillEntity skillEntity = skillToSkillEntity.convert(skill);

            if (isCategoryExist(skillEntity.getCategoryEntities())) {
                skillRepository.save(skillEntity);
            } else {
                throw new CategoryNotFound();
            }
        } else {
            throw new SkillNotFound();
        }
    }

    public void deleteSkill(int id) {
        Optional<SkillEntity> skillEntityOptional = skillRepository.findById(id);

        if (!skillEntityOptional.isPresent()) {
            throw new SkillNotFound();
        } else {
            SkillEntity skillEntity = skillEntityOptional.get();

            //delete skill in table user
            for (UserEntity userEntity : skillEntity.getUserEntities()) {
                userEntity.getSkillEntities().remove(skillEntity);
                userRepository.save(userEntity);
            }
            //delete skill in table skill
            skillRepository.delete(skillEntity);
        }
    }
}
