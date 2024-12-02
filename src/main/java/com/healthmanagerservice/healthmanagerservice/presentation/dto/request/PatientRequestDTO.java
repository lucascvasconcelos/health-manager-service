package com.healthmanagerservice.healthmanagerservice.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record PatientRequestDTO(
        @NotBlank(message = "Name cannot be empty")
        String name,

        @NotBlank(message = "CPF cannot be empty")
        @Pattern(regexp = "\\d{11}", message = "CPF must be exactly 11 digits")
        String cpf,

        @NotNull(message = "Birth date cannot be null")
        LocalDate birthDate,

        @Positive(message = "Weight must be a positive value")
        Double weight,

        @Positive(message = "Height must be a positive value")
        Double height,

        @NotBlank(message = "State cannot be empty")
        String state
) {}