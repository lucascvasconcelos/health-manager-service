package com.healthmanagerservice.healthmanagerservice.application.service;

import com.healthmanagerservice.healthmanagerservice.presentation.dto.request.UserRequestDTO;
import com.healthmanagerservice.healthmanagerservice.presentation.dto.response.UserResponseDTO;

import java.util.List;

public interface UserService {
    UserResponseDTO createUser(UserRequestDTO request);
    List<UserResponseDTO> getAllUsers();
}
