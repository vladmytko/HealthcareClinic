package com.vladyslav.HealthcareClinic.controller;

import com.vladyslav.HealthcareClinic.dto.RegisterStaffRequest;
import com.vladyslav.HealthcareClinic.dto.Response;
import com.vladyslav.HealthcareClinic.entity.Staff;
import com.vladyslav.HealthcareClinic.service.interfac.IStaffService;
import jakarta.validation.Valid;
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

    @PatchMapping("/update-staff/{staffId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateStaff(@PathVariable Long staffId,
                                                @RequestBody Map<String,String> requestBody) {

        String firstName = requestBody.get("firstName");
        String lastName = requestBody.get("lastName");
        String email = requestBody.get("email");
        String phoneNumber = requestBody.get("phoneNumber");
        String address = requestBody.get("address");
        String dateOfBirthStr = requestBody.get("dateOfBirth");
        String specialisation = requestBody.get("specialisation");

        // Handle date parsing safety
        LocalDate dateOfBirth = null;
        if(dateOfBirthStr != null && !dateOfBirthStr.isEmpty()) {
            try{
                dateOfBirth = LocalDate.parse(dateOfBirthStr);
            } catch (DateTimeException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(400, "Invalid date format. Use YYYY-MM-DD."));
            }
        }

        Response response = staffService.updateStaff(staffId, firstName, lastName, email, phoneNumber, address, dateOfBirth, specialisation);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // Update method for staff, system automatically gets staff id and provides ability to edit account based on id
    @PatchMapping("/update-self-staff")
    @PreAuthorize("hasAuthority('STAFF')")
    public ResponseEntity<Response> updateSelfStaff(@RequestBody Map<String,String> requestBody) {

        // Get staff ID of logged in staff from JWT token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String fetchedEmail = authentication.getName(); // Get email from JWT token
        Long staffId = staffService.getStaffIdByEmail(fetchedEmail);

        if(staffId == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(400, "Staff not found"));
        }

        String firstName = requestBody.get("firstName");
        String lastName = requestBody.get("lastName");
        String phoneNumber = requestBody.get("phoneNumber");
        String address = requestBody.get("address");
        String dateOfBirthStr = requestBody.get("dateOfBirth");

        // Handle date parsing safety
        LocalDate dateOfBirth = null;
        if(dateOfBirthStr != null && !dateOfBirthStr.isEmpty()) {
            try{
                dateOfBirth = LocalDate.parse(dateOfBirthStr);
            } catch (DateTimeException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(400, "Invalid date format. Use YYYY-MM-DD."));
            }
        }

        Response response = staffService.updateSelfStaff(staffId, firstName, lastName, phoneNumber, address, dateOfBirth);
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
