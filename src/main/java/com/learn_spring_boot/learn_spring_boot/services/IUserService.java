package com.learn_spring_boot.learn_spring_boot.services;

import com.learn_spring_boot.learn_spring_boot.dtos.UserDTO;
import com.learn_spring_boot.learn_spring_boot.models.User;
import com.learn_spring_boot.learn_spring_boot.response.UserResponse;

public interface IUserService {
    void createUser(UserDTO userDTO) throws Exception;
    UserResponse login(String phoneNumber, String password) throws Exception;

    User getUserFromToken(String phoneNumber) throws Exception;
}
