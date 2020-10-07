package com.example.HRM.BE.converters.SkillConverter;

import com.example.HRM.BE.DTO.Category;
import com.example.HRM.BE.DTO.Skill;
import com.example.HRM.BE.converters.Bases.Converter;
import com.example.HRM.BE.entities.CategoryEntity;
import com.example.HRM.BE.entities.SkillEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SkillEntityToSkill extends Converter<SkillEntity, Skill> {

    @Autowired
    private Converter<CategoryEntity, Category> categoryEntityToCategory;

    @Override
    public Skill convert(SkillEntity source) {
        Skill skill = new Skill();

        skill.setId(source.getId());
        skill.setName(source.getName());
        skill.setCategory(categoryEntityToCategory.convert(source.getCategoryEntity()));

        return skill;
    }
}
