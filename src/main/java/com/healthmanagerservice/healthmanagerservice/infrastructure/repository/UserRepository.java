package com.healthmanagerservice.healthmanagerservice.infrastructure.repository;

import com.healthmanagerservice.healthmanagerservice.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String username);
}
