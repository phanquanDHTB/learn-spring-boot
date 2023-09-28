package com.learn_spring_boot.learn_spring_boot.dtos;

import lombok.Data;

@Data
public class OrderItemDTO {
    private Long productId;
    private Integer numberOfProduct;
}
