package com.healthmanagerservice.healthmanagerservice.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AuthRequest(
        @NotBlank
        String password,
        @NotBlank
        String username
) {}
