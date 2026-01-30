package com.example.user_service.repository;

import com.example.user_service.entity.UserServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserServiceRepository extends JpaRepository<UserServiceEntity, Long> {
    boolean existsByEmail(String email);
    UserServiceEntity findByEmail(String email);
}
