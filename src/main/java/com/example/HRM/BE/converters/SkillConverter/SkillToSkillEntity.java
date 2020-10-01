package com.example.HRM.BE.converters.SkillConverter;

import com.example.HRM.BE.DTO.Category;
import com.example.HRM.BE.DTO.Skill;
import com.example.HRM.BE.converters.bases.Converter;
import com.example.HRM.BE.entities.CategoryEntity;
import com.example.HRM.BE.entities.SkillEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SkillToSkillEntity extends Converter<Skill, SkillEntity> {

//    @Autowired
//    private Converter<Category, CategoryEntity> categoryToCategoryEntity;

    @Override
    public SkillEntity convert(Skill source) {
        SkillEntity skillEntity = new SkillEntity();

        skillEntity.setId(source.getId());
        skillEntity.setName(source.getName());
//        skillEntity.setCategoryEntity(categoryToCategoryEntity.convert(source.getCategory()));

        return skillEntity;
    }
}
