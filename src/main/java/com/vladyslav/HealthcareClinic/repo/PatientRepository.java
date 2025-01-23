package com.vladyslav.HealthcareClinic.repo;

import com.vladyslav.HealthcareClinic.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    boolean existsByEmail(String email);

    @Query("SELECT p FROM Patient p WHERE LOWER(p.email) = LOWER(:email)")
    Optional<Patient> findByEmail(@Param("email") String email);

    @Query("SELECT p FROM Patient p WHERE p.firstName = :firstName AND p.lastName = :lastName")
    Optional<Patient> findPatientByName(@Param("firstName") String firstName, @Param("lastName") String lastName);

}
