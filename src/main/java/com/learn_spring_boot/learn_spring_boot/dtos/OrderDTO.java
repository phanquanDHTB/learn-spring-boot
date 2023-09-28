package com.learn_spring_boot.learn_spring_boot.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    @Min(value = 1, message = "User id must be greater than 0")
    private Long userId;
    private String fullName;
    private String email;
    @NotBlank(message = "Phone number is require")
    @Size(min = 10, max = 10)
    private String phoneNumber;
    private String address;
    private String note;
    @Min(value = 0, message = "Price must be greater than or equal 0")
    private Float totalMoney;
    private String shippingMethod;
    private LocalDate shippingDate;
    private String shippingAddress;
    private String paymentMethod;
    private List<OrderItemDTO> listItem;
}
