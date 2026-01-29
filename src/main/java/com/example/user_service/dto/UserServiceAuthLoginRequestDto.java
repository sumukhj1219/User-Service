package com.example.user_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserServiceAuthLoginRequestDto {
    private String username;
    private String email;
    private String password;
}
