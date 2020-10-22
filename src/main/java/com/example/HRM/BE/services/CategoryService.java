package com.example.HRM.BE.services;

import com.example.HRM.BE.DTO.Category;
import com.example.HRM.BE.converters.Bases.Converter;
import com.example.HRM.BE.entities.CategoryEntity;
import com.example.HRM.BE.entities.SkillEntity;
import com.example.HRM.BE.entities.UserEntity;
import com.example.HRM.BE.exceptions.CategoryException.CategoryHasExisted;
import com.example.HRM.BE.exceptions.CategoryException.CategoryNotFound;
import com.example.HRM.BE.repositories.CategoryRepository;
import com.example.HRM.BE.repositories.SkillRepository;
import com.example.HRM.BE.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.HRM.BE.common.Constants.PENDING;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SkillRepository skillRepository;

    @Autowired
    private Converter<CategoryEntity, Category> categoryEntityToCategory;

    @Autowired
    private Converter<Category, CategoryEntity> categoryToCategoryEntity;

    public Category getCategoryFollowById(int id) {
        if (categoryRepository.findById(id).isPresent()) {
            return categoryEntityToCategory.convert(categoryRepository.findById(id).get());
        }
        throw new CategoryNotFound();
    }

    public List<Category> getAllCategories() {
        List<Category> categoryList = new ArrayList<>();

        for (CategoryEntity categoryEntity : categoryRepository.findAll()) {
            categoryList.add(categoryEntityToCategory.convert(categoryEntity));
        }
        return categoryList;
    }

    public void addNewCategory(Category category) {
        CategoryEntity categoryEntity = categoryToCategoryEntity.convert(category);

        if (categoryRepository.findByName(category.getName()).isPresent()) {
            throw new CategoryHasExisted();
        }
        categoryEntity.setStatus(PENDING);
        categoryRepository.save(categoryEntity);

    }

    public void editCategory(Category category) {
        Optional<CategoryEntity> categoryEntityOptional = categoryRepository.findById(category.getId());

        if (categoryEntityOptional.isPresent()) {
            CategoryEntity categoryEntity = categoryEntityOptional.get();
            categoryEntity.setName(category.getName());
            categoryEntity.setStatus(category.getStatus());


            categoryRepository.save(categoryEntity);
        } else {
            throw new CategoryNotFound();
        }
    }

    public void deleteCategoryFollowID(int id) {
        Optional<CategoryEntity> categoryEntityOptional = categoryRepository.findById(id);

        if (categoryEntityOptional.isPresent()) {
            CategoryEntity categoryEntity = categoryEntityOptional.get();

            for (SkillEntity skillEntity : categoryEntity.getSkillEntities()) {

                //delte skill in table user
                for (UserEntity userEntity : skillEntity.getUserEntities()) {
                    userEntity.getSkillEntities().remove(skillEntity);
                    userRepository.save(userEntity);
                }
                skillRepository.delete(skillEntity);
            }
            categoryRepository.delete(categoryEntity);
        } else {
            throw new CategoryNotFound();
        }
    }

    public List<Category> searchCategoryFollowKeyword(String key) {
        List<Category> categoryList = new ArrayList<>();

        for (CategoryEntity categoryEntity : categoryRepository.findAllByKeyword(key)) {
            categoryList.add(categoryEntityToCategory.convert(categoryEntity));
        }
        return categoryList;
    }
}
