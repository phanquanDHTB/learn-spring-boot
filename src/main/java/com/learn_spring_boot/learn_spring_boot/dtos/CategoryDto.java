package com.learn_spring_boot.learn_spring_boot.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CategoryDto {
    @NotEmpty(message = "Category's name cannot be empty")
    private String name;
}
