package com.learn_spring_boot.learn_spring_boot.services;

import com.learn_spring_boot.learn_spring_boot.dtos.CategoryDTO;
import com.learn_spring_boot.learn_spring_boot.exceptions.DataNotFoundException;
import com.learn_spring_boot.learn_spring_boot.models.Category;

import java.util.List;

public interface ICategoryService {
    Category createCategory(CategoryDTO categoryDto);
    Category getCategoryById(Long id) throws DataNotFoundException;
    List<Category> getAllCategories();
    Category updateCategory(Long id, CategoryDTO categoryDto);
    String deleteCategory(Long id);
}
