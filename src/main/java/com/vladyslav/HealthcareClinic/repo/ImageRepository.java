package com.vladyslav.HealthcareClinic.repo;

import com.vladyslav.HealthcareClinic.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query("SELECT i FROM Image i WHERE i.patient.id = :patientId")
    List<Image> findImagesByPatientId(@Param("patientId") Long patientId);

    @Query("SELECT i FROM Image i WHERE i.staff.id = :staffId")
    List<Image> findImagesByStaffId(@Param("staffId") Long staffId);
}
