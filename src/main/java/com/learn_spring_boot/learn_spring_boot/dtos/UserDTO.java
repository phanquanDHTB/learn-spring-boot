package com.learn_spring_boot.learn_spring_boot.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private String fullName;
    @NotBlank(message = "Phone number can not be empty")
    private String phoneNumber;
    private String address;
    @NotBlank(message = "Password can not be empty")
    private String password;
    private Date dateOfBirth;
    private int googleAccountId;
    private int facebookAccountId;
    private Long role;
}
