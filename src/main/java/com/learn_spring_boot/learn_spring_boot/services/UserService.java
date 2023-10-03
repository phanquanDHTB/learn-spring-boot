package com.learn_spring_boot.learn_spring_boot.services;

import com.learn_spring_boot.learn_spring_boot.configurations.JwtTokenProvider;
import com.learn_spring_boot.learn_spring_boot.dtos.UserDTO;
import com.learn_spring_boot.learn_spring_boot.exceptions.BadRequestException;
import com.learn_spring_boot.learn_spring_boot.exceptions.DataNotFoundException;
import com.learn_spring_boot.learn_spring_boot.models.Role;
import com.learn_spring_boot.learn_spring_boot.models.User;
import com.learn_spring_boot.learn_spring_boot.repositories.RoleRepository;
import com.learn_spring_boot.learn_spring_boot.repositories.UserRepository;
import com.learn_spring_boot.learn_spring_boot.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    @Override
    public void createUser(UserDTO userDTO) throws Exception{
        if(userRepository.existsByPhoneNumber(userDTO.getPhoneNumber())){
            throw new BadRequestException("Phone number already exists");
        }
        Role userRole = roleRepository.findById(userDTO.getRole())
                .orElseThrow(() -> new BadRequestException("Role id not found"));
        if(userRole.getName().equals("admin")){
            throw new BadRequestException("Can not create admin account");
        }
        User newUser = User.builder()
                .fullName(userDTO.getFullName())
                .phoneNumber(userDTO.getPhoneNumber())
                .password(userDTO.getPassword())
                .address(userDTO.getAddress())
                .dateOfBirth(userDTO.getDateOfBirth())
                .facebookAccountId(userDTO.getFacebookAccountId())
                .googleAccountId(userDTO.getGoogleAccountId())
                .role(userRole)
                .active(true)
                .build();
        if(userDTO.getFacebookAccountId() == 0 && userDTO.getGoogleAccountId() == 0){
            String password = userDTO.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            newUser.setPassword(encodedPassword);
        }
        userRepository.save(newUser);
    }

    @Override
    public UserResponse login(String phoneNumber, String password) throws Exception {
        Optional<User> optionalUser = userRepository.findByPhoneNumber(phoneNumber);
        if(optionalUser.isEmpty()){
            throw new DataNotFoundException("Invalid phone number or password");
        }
        User existingUser = optionalUser.get();
        // check password
        if(existingUser.getFacebookAccountId() == 0 && existingUser.getGoogleAccountId() == 0){
            if(!passwordEncoder.matches(password, existingUser.getPassword())){
                throw new BadRequestException("Login fail");
            }
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                phoneNumber, password, existingUser.getAuthorities()
        );
        //authenticate and generateToken
        authenticationManager.authenticate(authenticationToken);
        String token = jwtTokenProvider.generateToken(existingUser);
        UserResponse user = UserResponse.toUserResponse(token, existingUser);
        return user;
    }

}
