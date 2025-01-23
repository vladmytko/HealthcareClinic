package com.vladyslav.HealthcareClinic.service.interfac;

import com.vladyslav.HealthcareClinic.dto.RegisterStaffRequest;
import com.vladyslav.HealthcareClinic.dto.Response;
import com.vladyslav.HealthcareClinic.entity.Staff;

public interface IStaffService {

    Response addStaff(RegisterStaffRequest registerStaffRequest);

    Response getStaffById(Long staffId);

    Response getStaffByEmail(String email);

    Response getStaffByName(String firstName, String lastName);

    Response getAllStaff();

    Response deleteStaffById(Long staffId);

    Response updateStaff(Long staffId, String firstName, String lastName, String email, String phoneNumber, String specialisation);

    Response getStaffInfoById(Long staffId);

    Response getStaffInfoByEmail(String email);
}
