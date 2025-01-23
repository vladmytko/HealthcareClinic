package com.vladyslav.HealthcareClinic.repo;

import com.vladyslav.HealthcareClinic.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task t WHERE t.staff.id = :staffId")
    List<Task> findTaskByStaffId(@Param("staffId") Long staffId);

    @Query("SELECT t FROM Task t WHERE t.patient.id = :patientId")
    List<Task> findTaskByPatientId(@Param("patientId") Long patientId);

}
