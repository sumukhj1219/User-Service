package com.example.user_service.controller;

import com.example.user_service.entity.Role;
import com.example.user_service.payload.ApiResponse;
import com.example.user_service.service.UserServiceRole;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserRoleController {
    private final UserServiceRole userServiceRole;

    public UserRoleController(UserServiceRole userServiceRole) {
    this.userServiceRole = userServiceRole;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_HR')")
    @PostMapping("/{userId}/roles")
    public ApiResponse<?> assignRole(
            @PathVariable String userId,
            @RequestParam Role role
    ) {
        userServiceRole.assignRole(userId, role);
        return new ApiResponse<>("Role assigned successfully", 200, null);
    }
}
