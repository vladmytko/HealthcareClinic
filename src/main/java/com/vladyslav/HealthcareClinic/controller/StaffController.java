package com.vladyslav.HealthcareClinic.controller;

import com.vladyslav.HealthcareClinic.dto.RegisterStaffRequest;
import com.vladyslav.HealthcareClinic.dto.Response;
import com.vladyslav.HealthcareClinic.entity.Staff;
import com.vladyslav.HealthcareClinic.service.interfac.IStaffService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private IStaffService staffService;

    @PostMapping("/add-staff")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> addStaff(@Valid @RequestBody RegisterStaffRequest registerStaffRequest) {
        Response response = staffService.addStaff(registerStaffRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-staff-by-id/{staffId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getStaffById(@PathVariable Long staffId) {
        Response response = staffService.getStaffById(staffId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-staff-by-email/{email}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getStaffByEmail(@PathVariable String email) {
        Response response = staffService.getStaffByEmail(email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-staff-by-name/{firstName}/{lastName}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getStaffByName(@PathVariable String firstName, @PathVariable String lastName) {
        Response response = staffService.getStaffByName(firstName,lastName);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-all-staff")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAllStaff() {
        Response response = staffService.getAllStaff();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete-staff/{staffId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteStaff(@PathVariable Long staffId) {
        Response response = staffService.deleteStaffById(staffId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/update-staff/{staffId}")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('STAFF')")
    public ResponseEntity<Response> updateStaff(@PathVariable Long staffId,
                                                @RequestParam(value = "firstName", required = false) String firstName,
                                                @RequestParam(value = "lastName", required = false) String lastName,
                                                @RequestParam(value = "email", required = false) String email,
                                                @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
                                                @RequestParam(value = "specialisation", required = false) String specialisation) {
        Response response = staffService.updateStaff(staffId, firstName, lastName, email, phoneNumber, specialisation);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-staff-info-by-id/{staffId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getStaffInfoById(@PathVariable Long staffId) {
        Response response = staffService.getStaffInfoById(staffId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-staff-info-by-email/{email}")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('STAFF')")
    public ResponseEntity<Response> getStaffInfoByEmail(@PathVariable String email) {
        Response response = staffService.getStaffInfoByEmail(email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


    @GetMapping("/get-logged-in-staff-info")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('STAFF')")
    public ResponseEntity<Response> getLoggedInStaffInfoByEmail() {

        // Get the authenticated user's email
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        // Call the service to fetch staff info
        Response response = staffService.getStaffInfoByEmail(email);

        // Return the response with the appropriate HTTP status code
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }






}
