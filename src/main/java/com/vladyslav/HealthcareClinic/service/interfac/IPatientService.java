package com.vladyslav.HealthcareClinic.service.interfac;

import com.vladyslav.HealthcareClinic.dto.response.Response;
import com.vladyslav.HealthcareClinic.entity.Patient;

import java.time.LocalDate;

public interface IPatientService {

    Response addPatient(Patient patient);

    Response getPatientById(Long patientId);

    Response getPatientByEmail(String email);

    Response getPatientByName(String firstName, String lastName);

    Response getAllPatients();

    Response deletePatientById(Long patientId);

    Response updatePatient(Long patientId,
                           String firstName,
                           String lastName,
                           String address,
                           String email,
                           String phoneNumber,
                           LocalDate dateOfBirth,
                           String diagnosis,
                           String condition
                           );

    Response updateSelfPatient(Long patientId,
                           String firstName,
                           String lastName,
                           String address,
                           String email,
                           String phoneNumber,
                           LocalDate dateOfBirth
    );

    Response updatePatientDiagnosis(Long patientId, String diagnosis, String condition);

    Long getPatientIdByEmail(String email);







}