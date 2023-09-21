package com.learn_spring_boot.learn_spring_boot.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    @Size(min = 3, max = 350, message = "Product name cannot be empty")
    private String name;

    private Float price;

    private String thumbnail;

    private String description;

    private Long categoryId;
}
