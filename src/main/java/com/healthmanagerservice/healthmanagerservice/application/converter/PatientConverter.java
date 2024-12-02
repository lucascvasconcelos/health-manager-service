package com.healthmanagerservice.healthmanagerservice.application.converter;

import com.healthmanagerservice.healthmanagerservice.domain.entities.Patient;
import com.healthmanagerservice.healthmanagerservice.infrastructure.util.CryptoUtil;
import com.healthmanagerservice.healthmanagerservice.presentation.dto.request.PatientRequestDTO;
import com.healthmanagerservice.healthmanagerservice.presentation.dto.response.PatientResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class PatientConverter {

    private CryptoUtil cryptoUtil = new CryptoUtil();

    public PatientConverter() throws Exception {
    }

    public PatientResponseDTO toDTO(Patient patient) {
        try {
            return new PatientResponseDTO(
                    patient.getId(),
                    patient.getName(),
                    cryptoUtil.decrypt(patient.getCpf()),
                    patient.getBirthDate(),
                    patient.getWeight(),
                    patient.getHeight(),
                    patient.getState()
            );
        } catch (Exception e) {
            throw new RuntimeException("Error decrypting CPF: " + e.getMessage());
        }
    }

    public Patient toEntity(PatientRequestDTO patientRequestDTO) {
        try {
            Patient patient = new Patient();
            patient.setName(patientRequestDTO.name());
            patient.setCpf(cryptoUtil.encrypt(patientRequestDTO.cpf()));
            patient.setBirthDate(patientRequestDTO.birthDate());
            patient.setWeight(patientRequestDTO.weight());
            patient.setHeight(patientRequestDTO.height());
            patient.setState(patientRequestDTO.state());
            return patient;
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting CPF: "+ e.getMessage());
        }
    }
}
