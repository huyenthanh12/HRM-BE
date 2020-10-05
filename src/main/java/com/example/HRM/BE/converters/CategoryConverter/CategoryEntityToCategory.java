package com.example.HRM.BE.converters.CategoryConverter;

import com.example.HRM.BE.DTO.Category;
import com.example.HRM.BE.converters.bases.Converter;
import com.example.HRM.BE.entities.CategoryEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoryEntityToCategory extends Converter<CategoryEntity, Category> {

    @Override
    public Category convert(CategoryEntity source) {
        Category category = new Category();
        category.setId(source.getId());
        category.setName(source.getName());
        category.setStatus(source.getStatus());

        return category;
    }
}
