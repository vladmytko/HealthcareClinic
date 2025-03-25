package com.vladyslav.HealthcareClinic.service.interfac;

import com.vladyslav.HealthcareClinic.dto.requests.RegisterStaffRequest;
import com.vladyslav.HealthcareClinic.dto.response.Response;

import java.time.LocalDate;

public interface IStaffService {

    Response addStaff(RegisterStaffRequest registerStaffRequest);

    Response getStaffById(Long staffId);

    Response getStaffByEmail(String email);

    Response getStaffByName(String firstName, String lastName);

    Response getAllStaff();

    Response deleteStaffById(Long staffId);

    Response updateStaff(Long staffId,
                         String firstName,
                         String lastName,
                         String email,
                         String phoneNumber,
                         String address,
                         LocalDate dateOfBirth,
                         String specialisation);

    Response updateSelfStaff(Long patientId,
                             String firstName,
                             String lastName,
                             String phoneNumber,
                             String address,
                             LocalDate dateOfBirth);

    Long getStaffIdByEmail(String email);
;
    Response getStaffInfoById(Long staffId);

    Response getStaffInfoByEmail(String email);
}
