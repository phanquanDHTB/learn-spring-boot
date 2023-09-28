package com.learn_spring_boot.learn_spring_boot.controllers;

import com.learn_spring_boot.learn_spring_boot.dtos.UserDTO;
import com.learn_spring_boot.learn_spring_boot.dtos.UserLoginDTO;
import com.learn_spring_boot.learn_spring_boot.models.User;
import com.learn_spring_boot.learn_spring_boot.services.UserService;
import com.learn_spring_boot.learn_spring_boot.util.ResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(ResponseUtil.badRequest(errorMessages.get(0)));
        }
        try{
            userService.createUser(userDTO);
            return ResponseEntity.ok().body(ResponseUtil.message("Successfully"));
        } catch (Exception exception){
            return ResponseEntity.badRequest().body(ResponseUtil.badRequest(exception.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@Valid @RequestBody UserLoginDTO userLoginDTO, BindingResult result){
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(ResponseUtil.badRequest(errorMessages.get(0)));
        }
        try {
            String token = userService.login(
                    userLoginDTO.getUsername(),
                    userLoginDTO.getPassword()
            );
            // Trả về token trong response
            return ResponseEntity.ok().body(ResponseUtil.ok(token));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ResponseUtil.badRequest(e.getMessage())
            );
        }
    }
}
