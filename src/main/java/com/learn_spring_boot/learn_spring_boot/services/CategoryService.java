package com.learn_spring_boot.learn_spring_boot.services;

import com.learn_spring_boot.learn_spring_boot.dtos.CategoryDTO;
import com.learn_spring_boot.learn_spring_boot.exceptions.BadRequestException;
import com.learn_spring_boot.learn_spring_boot.exceptions.DataNotFoundException;
import com.learn_spring_boot.learn_spring_boot.models.Category;
import com.learn_spring_boot.learn_spring_boot.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;
    @Override
    public Category createCategory(CategoryDTO categoryDto) {
        Category newCategory = Category.builder().name(categoryDto.getName()).build();
        return categoryRepository.save(newCategory);
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new BadRequestException("Category not found"));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category updateCategory(Long id, CategoryDTO categoryDto) {
        Category existingCategory = categoryRepository.findById(id).get();
        existingCategory.setName(categoryDto.getName());
        return categoryRepository.save(existingCategory);
    }

    @Override
    public String deleteCategory(Long id) {
        categoryRepository.deleteById(id);
        return "Delete successfully";
    }
}
