package com.vladyslav.HealthcareClinic.repo;

import com.vladyslav.HealthcareClinic.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StaffRepository extends JpaRepository<Staff, Long> {

    boolean existsByEmail(String email);

    @Query("SELECT s FROM Staff s WHERE LOWER(s.email) = LOWER(:email)")
    Optional<Staff> findByEmail(String email);

    @Query("SELECT s FROM Staff s WHERE s.firstName = :firstName AND s.lastName = :lastName")
    Optional<Staff> findByName(@Param("firstName") String firstName, @Param("lastName") String lastName);
}
