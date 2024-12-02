package com.healthmanagerservice.healthmanagerservice.presentation.dto.response;

import java.time.LocalDate;

public record PatientResponseDTO(
        Long id,
        String name,
        String cpf,
        LocalDate birthDate,
        Double weight,
        Double height,
        String state
) {}