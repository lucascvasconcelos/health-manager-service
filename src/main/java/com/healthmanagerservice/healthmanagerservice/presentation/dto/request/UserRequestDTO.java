package com.healthmanagerservice.healthmanagerservice.presentation.dto.request;

import com.healthmanagerservice.healthmanagerservice.domain.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRequestDTO(
    @NotBlank String name,
    @NotBlank String cpf,
    @NotBlank String password,
    @NotNull Role role
) {}