package com.vladyslav.HealthcareClinic.controller;

import com.vladyslav.HealthcareClinic.dto.Response;
import com.vladyslav.HealthcareClinic.entity.Patient;
import com.vladyslav.HealthcareClinic.service.interfac.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private IPatientService patientService;

//    @PostMapping("/add-patient")
//    public ResponseEntity<Response> addPatient(@RequestBody Patient patient) {
//        Response response = patientService.addPatient(patient);
//        return ResponseEntity.status(response.getStatusCode()).body(response);
//    }

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

    @GetMapping("/get-patients-info/{email}")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('STAFF')")
    public ResponseEntity<Response> getPatientInfo(@PathVariable String email) {
        Response response = patientService.getPatientInfo(email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-logged-in-patients-info")
    public ResponseEntity<Response> getLoggedInPatientInfoByEmail() {

        // Get the authenticated user's email
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        // Call the service to fetch staff info
        Response response = patientService.getPatientInfo(email);

        // Return the response with the appropriate HTTP status code
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete-patient-by-id/{patientId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deletePatientById(@PathVariable Long patientId) {
        Response response = patientService.deletePatientById(patientId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


    // Maybe need to use authenticated patient in this case to let patient update its details
    @PutMapping("/update-patient/{patientId}")
    // @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('STAFF')")
    public ResponseEntity<Response> updatePatient(@PathVariable Long patientId,
                                                  @RequestParam(value = "firstName", required = false) String firstName,
                                                  @RequestParam(value = "lastName", required = false) String lastName,
                                                  @RequestParam(value = "address", required = false) String address,
                                                  @RequestParam(value = "email", required = false) String email,
                                                  @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
                                                  @RequestParam(value = "dateOfBirth", required = false) LocalDate dateOfBirth) {



        Response response = patientService.updatePatient(patientId, firstName, lastName, address, email, phoneNumber, dateOfBirth);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/update-patient-diagnosis/{patientId}")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('STAFF')")
    public ResponseEntity<Response> updatePatientDiagnosis(@PathVariable Long patientId,
                                                           @RequestParam(value = "diagnosis", required = false) String diagnosis,
                                                           @RequestParam(value = "condition", required = false) String condition) {
        Response response = patientService.updatePatientDiagnosis(patientId, diagnosis, condition);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}
