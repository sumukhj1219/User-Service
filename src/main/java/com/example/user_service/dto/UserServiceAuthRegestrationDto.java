package com.example.user_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserServiceAuthRegestrationDto {
    private String id;
    private String username;
    private String email;
    private String password;
    private String userType;
}
