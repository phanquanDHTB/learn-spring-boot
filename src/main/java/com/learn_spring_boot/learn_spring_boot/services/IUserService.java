package com.learn_spring_boot.learn_spring_boot.services;

import com.learn_spring_boot.learn_spring_boot.dtos.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService {
    void createUser(UserDTO userDTO) throws Exception;
    String login(String phoneNumber, String password) throws Exception;
}
