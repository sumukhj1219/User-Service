package com.example.user_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserServiceEntity {
    @Id
    @Column(name="user_id")
    private String Id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "profilePicture")
    private String profilePicture;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="user_roles", joinColumns = @JoinColumn(name="user_id"))
    @Column(name = "role")
    private Set<String> roles = new HashSet<>();
}
