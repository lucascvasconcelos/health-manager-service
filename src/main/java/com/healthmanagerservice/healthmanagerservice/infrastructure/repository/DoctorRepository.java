package com.healthmanagerservice.healthmanagerservice.infrastructure.repository;

import com.healthmanagerservice.healthmanagerservice.domain.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}


