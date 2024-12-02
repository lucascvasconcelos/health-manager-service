package com.healthmanagerservice.healthmanagerservice.presentation.dto.response;

import com.healthmanagerservice.healthmanagerservice.domain.enums.Role;

public record UserResponseDTO(
        Long id,
        String name,
        String cpf,
        Role role
) {}