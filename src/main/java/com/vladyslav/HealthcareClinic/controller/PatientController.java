package com.vladyslav.HealthcareClinic.controller;

import com.vladyslav.HealthcareClinic.dto.response.Response;
import com.vladyslav.HealthcareClinic.service.interfac.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private IPatientService patientService;

    @GetMapping("/get-patient-by-id/{patientId}")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('STAFF')")
    public ResponseEntity<Response> getPatientById(@PathVariable Long patientId) {
        Response response = patientService.getPatientById(patientId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-patient-by-email/{email}")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('STAFF')")
    public ResponseEntity<Response> getPatientByEmail(@PathVariable String email) {
        Response response = patientService.getPatientByEmail(email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-patient-by-name/{firstName}/{lastName}")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('STAFF')")
    public ResponseEntity<Response> addPatientByName(@PathVariable String firstName, @PathVariable String lastName) {
        Response response = patientService.getPatientByName(firstName,lastName);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-all-patients")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAllPatients() {
        Response response = patientService.getAllPatients();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-logged-in-patients-info")
    public ResponseEntity<Response> getLoggedInPatientInfoByEmail() {

        // Get the authenticated user's email
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        // Call the service to fetch staff info
        Response response = patientService.getPatientByEmail(email);

        // Return the response with the appropriate HTTP status code
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete-patient-by-id/{patientId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deletePatientById(@PathVariable Long patientId) {
        Response response = patientService.deletePatientById(patientId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // Update patient details method from ADMIN and STAFF, requires manually enter patient ID
    @PatchMapping("/update-patient/{patientId}")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('STAFF')")
    public ResponseEntity<Response> updatePatient(@PathVariable Long patientId,
                                                  @RequestBody Map<String, String> requestBody) {

        String firstName = requestBody.get("firstName");
        String lastName = requestBody.get("lastName");
        String address = requestBody.get("address");
        String email = requestBody.get("email");
        String phoneNumber = requestBody.get("phoneNumber");
        String dateOfBirthStr = requestBody.get("dateOfBirth");
        String diagnosis = requestBody.get("diagnosis");
        String condition = requestBody.get("condition");

        // Handle data parsing safety
        LocalDate dateOfBirth = null;
        if(dateOfBirthStr != null && !dateOfBirthStr.isEmpty()) {
            try{
                dateOfBirth = LocalDate.parse(dateOfBirthStr);
            } catch (DateTimeException e) {
                return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(400, "Invalid date format. Use YYYY-MM-DD."));
            }
        }

        Response response = patientService.updatePatient(patientId, firstName, lastName, address, email, phoneNumber, dateOfBirth, diagnosis, condition);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


    // Update method for patient, it automatically gets logged in patient details and give assess to make updates
    @PatchMapping("/update-self-patient")
    @PreAuthorize("hasAuthority('PATIENT')")
    public ResponseEntity<Response> updateSelfPatient(@RequestBody Map<String, String> requestBody) {

        // Get patient ID automatically from the JWT token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String fetchedEmail = authentication.getName(); // Get email from JWT token
        Long patientId = patientService.getPatientIdByEmail(fetchedEmail); // Find patient ID


        if (patientId == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(400, "Patient not found"));
        }


        String firstName = requestBody.get("firstName");
        String lastName = requestBody.get("lastName");
        String address = requestBody.get("address");
        String email = requestBody.get("email");
        String phoneNumber = requestBody.get("phoneNumber");
        String dateOfBirthStr = requestBody.get("dateOfBirth");

        // Handle data parsing safety
        LocalDate dateOfBirth = null;
        if(dateOfBirthStr != null && !dateOfBirthStr.isEmpty()) {
            try{
                dateOfBirth = LocalDate.parse(dateOfBirthStr);
            } catch (DateTimeException e) {
                return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(400, "Invalid date format. Use YYYY-MM-DD."));
            }
        }

        Response response = patientService.updateSelfPatient(patientId, firstName, lastName, address, email, phoneNumber, dateOfBirth);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}
