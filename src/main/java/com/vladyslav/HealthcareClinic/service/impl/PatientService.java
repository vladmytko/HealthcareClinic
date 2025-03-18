package com.vladyslav.HealthcareClinic.service.impl;

import com.vladyslav.HealthcareClinic.dto.PatientDTO;
import com.vladyslav.HealthcareClinic.dto.Response;
import com.vladyslav.HealthcareClinic.entity.Patient;
import com.vladyslav.HealthcareClinic.entity.User;
import com.vladyslav.HealthcareClinic.exception.OurException;
import com.vladyslav.HealthcareClinic.repo.PatientRepository;
import com.vladyslav.HealthcareClinic.repo.UserRepository;
import com.vladyslav.HealthcareClinic.service.interfac.IPatientService;
import com.vladyslav.HealthcareClinic.utils.JWTUtils;
import com.vladyslav.HealthcareClinic.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService implements IPatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTUtils jwtUtils;


    @Override
    public Response addPatient(Patient patient) {
        Response response = new Response();

        try {
            if (patientRepository.existsByEmail(patient.getEmail())) {
                throw new OurException(patient.getEmail() + " already exists");
            }

            Patient savedPatient = patientRepository.save(patient);
            PatientDTO patientDTO = Utils.mapPatientEntityToPatientDTO(savedPatient);
            response.setStatusCode(201);
            response.setPatientDTO(patientDTO);

        } catch (OurException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred during patient registration " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getPatientById(Long patientId) {

        Response response = new Response();

        try {

            Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new OurException("Patient Not Found"));
            PatientDTO patientDTO = Utils.mapPatientEntityToPatientDTO(patient);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setPatientDTO(patientDTO);
        } catch (OurException e) {

            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {

            response.setStatusCode(500);
            response.setMessage("Error getting patient by id " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getPatientByEmail(String email) {
        Response response = new Response();

        try {
            Patient patient = patientRepository.findByEmail(email).orElseThrow(() -> new OurException("User Not Found"));
            PatientDTO patientDTO = Utils.mapPatientEntityToPatientDTO(patient);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setPatientDTO(patientDTO);
        } catch (OurException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting patient by email " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getPatientByName(String firstName, String lastName) {
        Response response = new Response();

        try {
            Patient patient = patientRepository.findPatientByName(firstName, lastName).orElseThrow(() -> new OurException("User Not Found"));
            PatientDTO patientDTO = Utils.mapPatientEntityToPatientDTO(patient);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setPatientDTO(patientDTO);
        } catch (OurException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting patient by name " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getAllPatients() {
        Response response = new Response();

        try {
            List<Patient> patientList = patientRepository.findAll();
            List<PatientDTO> patientDTOList = Utils.mapPatientListEntityToPatientListDTO(patientList);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setPatientList(patientDTOList);

        } catch (OurException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting all patients " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response deletePatientById(Long patientId) {
        Response response = new Response();

        try {
            patientRepository.findById(patientId).orElseThrow(() -> new OurException("User Not Found"));
            patientRepository.deleteById(patientId);
            response.setStatusCode(200);
            response.setMessage("successful");

        } catch (OurException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred deleting patient " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response updatePatient(Long patientId, String firstName, String lastName, String address, String email, String phoneNumber, LocalDate dateOfBirth, String diagnosis, String condition) {
        Response response = new Response();

        try {
            // Fetch patient details from database
            Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new OurException("User Not Found"));

            // Update the patient's details
            if (firstName != null) patient.setFirstName(firstName);
            if (lastName != null) patient.setLastName(lastName);
            if (address != null) patient.setAddress(address);
            if (email != null) {
                User user = userRepository.findById(patient.getUser().getId()).orElseThrow(() -> new OurException("User Not Found"));
                patient.setEmail(email);
                user.setEmail(email);
                userRepository.save(user);
            }
            if (phoneNumber != null) patient.setPhoneNumber(phoneNumber);
            if (dateOfBirth != null) {
                patient.setDateOfBirth(dateOfBirth);
            } else {
                patient.setDateOfBirth(patient.getDateOfBirth());
            }
            if(diagnosis != null) patient.setDiagnosis(diagnosis);
            if(condition != null) patient.setConditions(condition);


            // Save the updated patient
            Patient updatedPatient = patientRepository.save(patient);

            // Map the updated patient to a DTO
            PatientDTO patientDTO = Utils.mapPatientEntityToPatientDTO(updatedPatient);

            response.setStatusCode(200);
            response.setMessage("Successful");
            response.setPatientDTO(patientDTO);

        } catch (OurException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error updating patient " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response updateSelfPatient(Long patientId, String firstName, String lastName, String address, String email, String phoneNumber, LocalDate dateOfBirth) {
        Response response = new Response();

        try {
            // Fetch patient details from database
            Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new OurException("User Not Found"));

            // Update the patient's details
            if (firstName != null) patient.setFirstName(firstName);
            if (lastName != null) patient.setLastName(lastName);
            if (address != null) patient.setAddress(address);
            if (email != null) {
                User user = userRepository.findById(patient.getUser().getId()).orElseThrow(() -> new OurException("User Not Found"));
                patient.setEmail(email);
                user.setEmail(email);
                userRepository.save(user);
            }
            if (phoneNumber != null) patient.setPhoneNumber(phoneNumber);
            if (dateOfBirth != null) {
                patient.setDateOfBirth(dateOfBirth);
            } else {
                patient.setDateOfBirth(patient.getDateOfBirth());
            }


            // Save the updated patient
            Patient updatedPatient = patientRepository.save(patient);

            // Map the updated patient to a DTO
            PatientDTO patientDTO = Utils.mapPatientEntityToPatientDTO(updatedPatient);

            response.setStatusCode(200);
            response.setMessage("Successful");
            response.setPatientDTO(patientDTO);

        } catch (OurException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error updating patient " + e.getMessage());
        }

        return response;
    }

    public Response updatePatientDiagnosis(Long patientId, String diagnosis, String condition) {

        Response response = new Response();

        try {
            // Fetch details from database
            Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new OurException("User Not Found"));

            //Update the patient's details
            if (diagnosis != null) patient.setDiagnosis(diagnosis);
            if (condition != null) patient.setConditions(condition);

            // Save the updated patient
            Patient updatedPatient = patientRepository.save(patient);

            // Map the updated patient to a DTO
            PatientDTO patientDTO = Utils.mapPatientEntityToPatientDTO(updatedPatient);

            response.setStatusCode(200);
            response.setMessage("Successful");
            response.setPatientDTO(patientDTO);
        } catch (OurException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error updating patient condition and diagnosis" + e.getMessage());
        }

        return response;

    }

    /**
     * Retrieves the logged-in patient's ID from the JWT token.
     */
    public Long getPatientIdByEmail(String email) {
        Optional<Patient> patient = patientRepository.findByEmail(email);
        return patient.map(Patient::getId).orElse(null);
    }

}
