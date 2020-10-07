package com.example.HRM.BE.converters.CategoryConverter;

import com.example.HRM.BE.DTO.Category;
import com.example.HRM.BE.DTO.Skill;
import com.example.HRM.BE.converters.Bases.Converter;
import com.example.HRM.BE.entities.CategoryEntity;
import com.example.HRM.BE.entities.SkillEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryEntityToCategory extends Converter<CategoryEntity, Category> {

    @Autowired
    private Converter<SkillEntity, Skill> skillEntityToSkill;
    @Override
    public Category convert(CategoryEntity source) {
        Category category = new Category();

        category.setId(source.getId());
        category.setName(source.getName());
        category.setStatus(source.getStatus());

        return category;
    }
}
