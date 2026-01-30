package com.example.user_service.dto;

import com.example.user_service.entity.UserType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserServiceUpdateProfileResponseDto {
    private String username;
    private String email;
    private String profilePicture;
    private UserType userType;
}
