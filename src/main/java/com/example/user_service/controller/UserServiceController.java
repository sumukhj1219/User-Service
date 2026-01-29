package com.example.user_service.controller;

import com.example.user_service.dto.UserServiceAuthLoginRequestDto;
import com.example.user_service.dto.UserServiceAuthLoginResponseDto;
import com.example.user_service.dto.UserServiceAuthRegestrationRequestDto;
import com.example.user_service.dto.UserServiceAuthRegestrationResponseDto;
import com.example.user_service.payload.ApiResponse;
import com.example.user_service.service.UserServiceAuthentication;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserServiceController {

    private final UserServiceAuthentication userServiceAuthentication;

    public UserServiceController(UserServiceAuthentication userServiceAuthentication) {
        this.userServiceAuthentication = userServiceAuthentication;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserServiceAuthRegestrationResponseDto>> register(
            @Valid @RequestBody UserServiceAuthRegestrationRequestDto req
            ) {
        var serviceResponse = userServiceAuthentication.register(req);
        ApiResponse<UserServiceAuthRegestrationResponseDto> response =
                new ApiResponse<>("User registered successfully", 200, serviceResponse);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserServiceAuthLoginResponseDto>> login(
            @Valid @RequestBody UserServiceAuthLoginRequestDto req
            ) {
        var serviceResponse = userServiceAuthentication.login(req);
        ApiResponse<UserServiceAuthLoginResponseDto> response =
                new ApiResponse<>("User logged in", 200, serviceResponse);
        return ResponseEntity.ok(response);
    }
}
