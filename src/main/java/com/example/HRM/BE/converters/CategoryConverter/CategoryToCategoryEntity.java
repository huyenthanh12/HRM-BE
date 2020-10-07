package com.example.HRM.BE.converters.CategoryConverter;

import com.example.HRM.BE.DTO.Category;
import com.example.HRM.BE.converters.Bases.Converter;
import com.example.HRM.BE.entities.CategoryEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoryToCategoryEntity extends Converter<Category, CategoryEntity> {

    @Override
    public CategoryEntity convert(Category source) {
        return new CategoryEntity(source.getId(), source.getName(), source.getStatus());
    }
}
