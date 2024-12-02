package com.healthmanagerservice.healthmanagerservice.application.service.impl;

import com.healthmanagerservice.healthmanagerservice.application.converter.PatientConverter;
import com.healthmanagerservice.healthmanagerservice.application.service.PatientService;
import com.healthmanagerservice.healthmanagerservice.domain.entities.Patient;
import com.healthmanagerservice.healthmanagerservice.infrastructure.repository.PatientRepository;
import com.healthmanagerservice.healthmanagerservice.infrastructure.external.IBGEStateClient;
import com.healthmanagerservice.healthmanagerservice.presentation.dto.request.PatientRequestDTO;
import com.healthmanagerservice.healthmanagerservice.presentation.dto.response.PatientResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService {
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private PatientConverter patientConverter;
    @Autowired
    private IBGEStateClient ibgeStateClient;

    public PatientResponseDTO createPatient(PatientRequestDTO patientDTO) {
        try {
            validateUf(patientDTO);
            Patient patient = patientConverter.toEntity(patientDTO);
            Patient savedPatient = patientRepository.save(patient);
            return patientConverter.toDTO(savedPatient);
        } catch (Exception e) {
            throw new RuntimeException("Error creating patient: " + e.getMessage());
        }
    }

    private void validateUf(PatientRequestDTO patientDTO) {
        if (isInvalidState(patientDTO)) {
            throw new IllegalArgumentException("UF Not Found: " + patientDTO.state());
        }
    }

    private boolean isInvalidState(PatientRequestDTO patientDTO) {
        return !retrieveBrazilianStateList().contains(patientDTO.state());
    }

    @Cacheable("brazilianStates")
    private List<String> retrieveBrazilianStateList() {
        return ibgeStateClient.getBrazilianStates();
    }

    public List<PatientResponseDTO> getAllPatients() {
        return patientRepository.findAll()
                .stream()
                .map(patientConverter::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<PatientResponseDTO> getPatientById(Long id) {
        return patientRepository.findById(id)
                .map(patientConverter::toDTO);
    }

    public Optional<PatientResponseDTO> updatePatient(Long id, PatientRequestDTO patientDTO) {
        return patientRepository.findById(id)
                .map(existingPatient -> {
                    Patient updatedPatient = patientConverter.toEntity(patientDTO);
                    updatedPatient.setId(id);
                    Patient savedPatient = patientRepository.save(updatedPatient);
                    return patientConverter.toDTO(savedPatient);
                });
    }

    public void deletePatientById(Long id) {
        patientRepository.deleteById(id);
    }
}
