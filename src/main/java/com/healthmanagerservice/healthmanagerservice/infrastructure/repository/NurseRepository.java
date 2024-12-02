package com.healthmanagerservice.healthmanagerservice.infrastructure.repository;

import com.healthmanagerservice.healthmanagerservice.domain.entities.Nurse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NurseRepository extends JpaRepository<Nurse, Long> {
}
