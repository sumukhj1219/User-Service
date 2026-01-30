package com.example.user_service.service;

import com.example.user_service.dto.UserServiceUpdateProfileRequestDto;
import com.example.user_service.dto.UserServiceUpdateProfileResponseDto;
import com.example.user_service.entity.UserServiceEntity;
import com.example.user_service.exception.BadRequestException;
import com.example.user_service.exception.NotFoundException;
import com.example.user_service.repository.UserServiceRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

@Service
public class UserServiceProfileUpdate {
    private final UserServiceRepository userServiceRepository;
    private final JwtService jwtService;

    public UserServiceProfileUpdate(UserServiceRepository userServiceRepository,
                                    JwtService jwtService
                                    ) {
        this.userServiceRepository = userServiceRepository;
        this.jwtService = jwtService;
    }

    public UserServiceUpdateProfileResponseDto updateProfile(String token, UserServiceUpdateProfileRequestDto req) {
        Long userId = Long.valueOf(jwtService.extractUserId(token));

        UserServiceEntity userEntity = userServiceRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (!userEntity.getEmail().equals(req.getEmail()) &&
                userServiceRepository.existsByEmail(req.getEmail())) {
            throw new BadRequestException("Email already exists");
        }

        userEntity.setUsername(req.getUsername());
        userEntity.setEmail(req.getEmail());
        userEntity.setProfilePicture(req.getProfilePicture());
        userEntity.setUserType(req.getUserType());

        userServiceRepository.save(userEntity);

        UserServiceUpdateProfileResponseDto res = new UserServiceUpdateProfileResponseDto();
        res.setUsername(userEntity.getUsername());
        res.setEmail(userEntity.getEmail());
        res.setProfilePicture(userEntity.getProfilePicture());
        res.setUserType(userEntity.getUserType());

        return res;
    }

}
