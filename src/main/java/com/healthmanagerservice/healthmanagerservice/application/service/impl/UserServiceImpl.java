package com.healthmanagerservice.healthmanagerservice.application.service.impl;

import com.healthmanagerservice.healthmanagerservice.application.converter.UserConverter;
import com.healthmanagerservice.healthmanagerservice.application.service.UserService;
import com.healthmanagerservice.healthmanagerservice.domain.entities.User;
import com.healthmanagerservice.healthmanagerservice.infrastructure.repository.UserRepository;
import com.healthmanagerservice.healthmanagerservice.presentation.dto.request.UserRequestDTO;
import com.healthmanagerservice.healthmanagerservice.presentation.dto.response.UserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public UserResponseDTO createUser(UserRequestDTO request) {
        User user = userConverter.toEntity(request);
        user.setPassword(encodePassword(user.getPassword()));

        try {
            User savedUser = userRepository.save(user);
            return userConverter.toDTO(savedUser);
        } catch (Exception e) {
            throw new RuntimeException("Error creating user: " + e.getMessage());
        }
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userConverter::toDTO)
                .collect(Collectors.toList());
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
