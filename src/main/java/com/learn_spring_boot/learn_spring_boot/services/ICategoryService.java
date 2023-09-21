package com.learn_spring_boot.learn_spring_boot.services;

import com.learn_spring_boot.learn_spring_boot.dtos.CategoryDto;
import com.learn_spring_boot.learn_spring_boot.models.Category;

import java.util.List;

public interface ICategoryService {
    Category createCategory(CategoryDto categoryDto);
    Category getCategoryById(Long id);
    List<Category> getAllCategories();
    Category updateCategory(Long id, CategoryDto categoryDto);
    String deleteCategory(Long id);
}
