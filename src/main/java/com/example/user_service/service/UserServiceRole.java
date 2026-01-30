package com.example.user_service.service;

import com.example.user_service.entity.Role;
import com.example.user_service.entity.UserServiceEntity;
import com.example.user_service.exception.BadRequestException;
import com.example.user_service.exception.NotFoundException;
import com.example.user_service.repository.UserServiceRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceRole {
    private final UserServiceRepository userServiceRepository;

    public UserServiceRole(UserServiceRepository userServiceRepository) {
        this.userServiceRepository = userServiceRepository;
    }

    public void assignRole(String userId, Role role) {
        if(role != Role.ROLE_ADMIN && role != Role.ROLE_HR) {
            throw new BadRequestException("Only ADMIN or HR roles can be assigned.");
        }

        UserServiceEntity user = userServiceRepository.findById(Long.valueOf(userId))
                .orElseThrow(()->new NotFoundException("User not found"));

        user.getRoles().add(role);
        userServiceRepository.save(user);
    }
}
