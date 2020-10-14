package com.example.HRM.BE.controllers;

import com.example.HRM.BE.DTO.Category;
import com.example.HRM.BE.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public Category getCategoryFollowByID(@PathVariable("id") int id) {
        return categoryService.getCategoryFollowById(id);
    }

    @PostMapping
    public void addNewCategory(@RequestBody @Validated Category category) {
        categoryService.addNewCategory(category);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping
    public void editCategory(@RequestBody @Validated Category category,
                             @PathParam("id") int id) {
        category.setId(id);
        categoryService.editCategory(category);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public void deleteCategoryFollowByID(@PathVariable("id") int id) {
        categoryService.deleteCategoryFollowID(id);
    }

    @GetMapping("/search")
    public List<Category> searchCategoryFollowKeyword(@RequestParam(name = "key") String key) {
        return categoryService.searchCategoryFollowKeyword(key);
    }
}
