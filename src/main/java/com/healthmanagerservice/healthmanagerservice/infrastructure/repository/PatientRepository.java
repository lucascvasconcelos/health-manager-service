package com.healthmanagerservice.healthmanagerservice.infrastructure.repository;

import com.healthmanagerservice.healthmanagerservice.domain.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
}
