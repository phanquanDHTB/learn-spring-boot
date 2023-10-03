package com.learn_spring_boot.learn_spring_boot.response;

import com.learn_spring_boot.learn_spring_boot.models.User;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String token;
    private Long userId;
    private String userName;
    public static UserResponse toUserResponse (String token, User user){
        return UserResponse
                .builder()
                .token(token)
                .userId(user.getId())
                .userName(user.getPhoneNumber())
                .build();
    }
}
