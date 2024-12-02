package com.healthmanagerservice.healthmanagerservice.application.service;

import com.healthmanagerservice.healthmanagerservice.presentation.dto.request.PatientRequestDTO;
import com.healthmanagerservice.healthmanagerservice.presentation.dto.response.PatientResponseDTO;

import java.util.List;
import java.util.Optional;

public interface PatientService {
    PatientResponseDTO createPatient(PatientRequestDTO patientDTO);

    Optional<PatientResponseDTO> getPatientById(Long id);

    List<PatientResponseDTO> getAllPatients();

    void deletePatientById(Long id);

    Optional<PatientResponseDTO> updatePatient(Long id, PatientRequestDTO patientDTO);
}
