package com.example.user_service.service;


import com.example.user_service.dto.UserServiceAuthLoginRequestDto;
import com.example.user_service.dto.UserServiceAuthLoginResponseDto;
import com.example.user_service.dto.UserServiceAuthRegestrationRequestDto;
import com.example.user_service.dto.UserServiceAuthRegestrationResponseDto;
import com.example.user_service.entity.UserServiceEntity;
import com.example.user_service.exception.BadRequestException;
import com.example.user_service.exception.NotFoundException;
import com.example.user_service.repository.UserServiceRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceAuthentication {

    private final UserServiceRepository userServiceRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserServiceAuthentication(UserServiceRepository userServiceRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.passwordEncoder = passwordEncoder;
        this.userServiceRepository = userServiceRepository;
        this.jwtService = jwtService;
    }

    public UserServiceAuthRegestrationResponseDto register(UserServiceAuthRegestrationRequestDto req) {

        if(userServiceRepository.existsByEmail(req.getEmail())){
            throw new BadRequestException("Email already exists");
        }

        UserServiceEntity userEntity = new UserServiceEntity();
        userEntity.setEmail(req.getEmail());
        userEntity.setUsername(req.getUsername());
        userEntity.setPassword(passwordEncoder.encode(req.getPassword()));
        userEntity.setUserType(req.getUserType());

        UserServiceEntity newUserEntity = userServiceRepository.save(userEntity);

        UserServiceAuthRegestrationResponseDto response = new UserServiceAuthRegestrationResponseDto();

        response.setUsername(newUserEntity.getUsername());
        response.setEmail(newUserEntity.getEmail());
        response.setUserType(newUserEntity.getUserType());
        response.setProfilePicture(newUserEntity.getProfilePicture());

        return response;
    }

    public UserServiceAuthLoginResponseDto login(UserServiceAuthLoginRequestDto req) {
        if(!userServiceRepository.existsByEmail(req.getEmail())) {
            throw new NotFoundException("Email not found");
        }

        UserServiceEntity userEntity = userServiceRepository.findByEmail(req.getEmail());

        if (!passwordEncoder.matches(req.getPassword(), userEntity.getPassword()) ||
            !userEntity.getEmail().equals(req.getEmail()) ||
                !userEntity.getUserType().equals(req.getUserType())
        ) {
            throw new BadRequestException("Invalid credentials");
        }

        String token = jwtService.generateToken(userEntity);

        UserServiceAuthLoginResponseDto response = new UserServiceAuthLoginResponseDto();
        response.setEmail(userEntity.getEmail());
        response.setUsername(userEntity.getUsername());
        response.setUserType(userEntity.getUserType());
        response.setToken(token);

        return response;
    }
}
