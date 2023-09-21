package com.learn_spring_boot.learn_spring_boot.controllers;

import com.learn_spring_boot.learn_spring_boot.dtos.CategoryDto;
import com.learn_spring_boot.learn_spring_boot.services.CategoryService;
import com.learn_spring_boot.learn_spring_boot.util.ResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/categories")
@RequiredArgsConstructor

public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<?> getAllCategories() {
        return ResponseEntity.ok(ResponseUtil.ok(categoryService.getAllCategories(), "Success"));
    }

    @PostMapping("")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDto categoryDto, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(ResponseUtil.badRequest(errorMessages.get(0)));
        }
        return ResponseEntity.ok(ResponseUtil.ok(categoryService.createCategory(categoryDto), "Success"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryDto categoryDto,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(ResponseUtil.badRequest(errorMessages.get(0)));
        }
        return ResponseEntity.ok(ResponseUtil.ok(categoryService.updateCategory(id, categoryDto), "Success"));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id){
        return ResponseEntity.ok(ResponseUtil.message(categoryService.deleteCategory(id)));
    }
}
