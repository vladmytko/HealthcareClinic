package com.vladyslav.HealthcareClinic.service.impl;

import com.vladyslav.HealthcareClinic.dto.*;
import com.vladyslav.HealthcareClinic.entity.Patient;
import com.vladyslav.HealthcareClinic.entity.Staff;
import com.vladyslav.HealthcareClinic.entity.User;
import com.vladyslav.HealthcareClinic.exception.OurException;
import com.vladyslav.HealthcareClinic.repo.StaffRepository;
import com.vladyslav.HealthcareClinic.repo.UserRepository;
import com.vladyslav.HealthcareClinic.service.interfac.IStaffService;
import com.vladyslav.HealthcareClinic.utils.JWTUtils;
import com.vladyslav.HealthcareClinic.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService  implements IStaffService {

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;


    @Override
    public Response addStaff(RegisterStaffRequest registerStaffRequest) {
        Response response = new Response();

        try {
            // Check if account exists
            if(staffRepository.existsByEmail(registerStaffRequest.getEmail())) {
                throw new OurException(registerStaffRequest.getEmail() + "already in use");
            }

            // Create a User entity
            User user = new User();
            user.setEmail(registerStaffRequest.getEmail());
            user.setPassword(passwordEncoder.encode(registerStaffRequest.getPassword()));
            user.setRole("STAFF");

            // Save the User entity
            User savedUser = userRepository.save(user);

            // Map User to User DTO
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(savedUser);

            // Create staff entity
            Staff staff = new Staff();
            staff.setFirstName(registerStaffRequest.getFirstName());
            staff.setLastName(registerStaffRequest.getLastName());
            staff.setEmail(registerStaffRequest.getEmail());
            staff.setPhoneNumber(registerStaffRequest.getPhoneNumber());
            staff.setAddress(registerStaffRequest.getAddress());
            staff.setDateOfBirth(registerStaffRequest.getDateOfBirth());
            staff.setSpecialisation(registerStaffRequest.getSpecialisation());
            staff.setUser(savedUser);


            // Save new staff
            Staff savedStaff = staffRepository.save(staff);
            StaffDTO staffDTO = Utils.mapStaffEntityToStaffDTO(savedStaff);

            // Set response
            response.setStatusCode(201);
            response.setMessage("successful");
            response.setStaffDTO(staffDTO);
            response.setUserDTO(userDTO);
        } catch (OurException e) {

            // Handle custom exception
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {

            // Handle unexpected error
            response.setStatusCode(500);
            response.setMessage("Error creating staff account" + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getStaffById(Long staffId) {
        Response response = new Response();

        try {
            // Fetch staff entity from database
            Staff staff = staffRepository.findById(staffId).orElseThrow(()-> new OurException("User Not Found"));

            // Map Staff entity to Staff PDO
            StaffDTO staffDTO = Utils.mapStaffEntityToStaffDTO(staff);


            // Set response
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setStaffDTO(staffDTO);
        } catch (OurException e) {

            // Handle custom exception
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {

            // Handle unexpected error
            response.setStatusCode(500);
            response.setMessage("Error getting staff by id" + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getStaffByEmail(String email) {
        Response response = new Response();

        try {
            // Fetch staff entity from database
            Staff staff = staffRepository.findByEmail(email).orElseThrow(()-> new OurException("Room Not Found"));

            // Map Staff entity to Staff PDO
            StaffDTO staffDTO = Utils.mapStaffEntityToStaffDTO(staff);

            // Set response
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setStaffDTO(staffDTO);
        } catch (OurException e) {

            // Handle custom exception
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {

            // Handle unexpected error
            response.setStatusCode(500);
            response.setMessage("Error getting user by email" + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getStaffByName(String firstName, String lastName) {
        Response response = new Response();

        try {
            // Fetch staff entity from database
            Staff staff = staffRepository.findByName(firstName, lastName).orElseThrow(()-> new OurException("User Not Found"));

            // Map Staff entity to Staff PDO
            StaffDTO staffDTO = Utils.mapStaffEntityToStaffDTO(staff);

            // Set response
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setStaffDTO(staffDTO);
        } catch (OurException e) {

            // Handle custom exception
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {

            // Handle unexpected error
            response.setStatusCode(500);
            response.setMessage("Error getting staff by name " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getAllStaff() {

        Response response = new Response();

        try {
            // Fetch staff entity from database
            List<Staff> staffList = staffRepository.findAll();

            // Save staff entity list to staff PDO list
           List<StaffDTO> staffDTOList = Utils.mapStaffListEntityToStaffListDTO(staffList);

            // Set response
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setStaffList(staffDTOList);
        } catch (OurException e) {

            // Handle custom exception
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {

            // Handle unexpected error
            response.setStatusCode(500);
            response.setMessage("Error getting all staff " + e.getMessage());
        }

        return response;

    }

    @Override
    public Response deleteStaffById(Long staffId) {
        Response response = new Response();

        try {
            // Fetch staff entity from database
            staffRepository.findById(staffId).orElseThrow(() -> new OurException("User Not Found"));

            // Delete staff account
            staffRepository.deleteById(staffId);

            // Set response
            response.setStatusCode(200);
            response.setMessage("successful");

        } catch (OurException e) {

            // Handle custom exception
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {

            // Handle unexpected error
            response.setStatusCode(500);
            response.setMessage("Error deleting staff account " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response updateStaff(Long staffId, String firstName, String lastName, String email, String phoneNumber, String specialisation) {

        Response response = new Response();

        try {
            // Fetch staff entity from database
            Staff staff = staffRepository.findById(staffId).orElseThrow(() -> new OurException("User Not Found"));

            // Update the staff details
            if(firstName != null) staff.setFirstName(firstName);
            if(lastName != null) staff.setLastName(lastName);
            if(email != null){
                User user = userRepository.findById(staff.getUser().getId()).orElseThrow(()->new OurException("User Not Found"));
                staff.setEmail(email);
                user.setEmail(email);

                userRepository.save(user);
            }
            if(phoneNumber != null) staff.setPhoneNumber(phoneNumber);
            if(specialisation != null) staff.setSpecialisation(specialisation);

            // Save updated staff entity
            Staff updatedStaff = staffRepository.save(staff);

            // Map the updated staff to DTO
            StaffDTO staffDTO = Utils.mapStaffEntityToStaffDTO(updatedStaff);

            // Set response
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setStaffDTO(staffDTO);

        } catch (OurException e) {

            // Handle custom exception
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {

            // Handle unexpected error
            response.setStatusCode(500);
            response.setMessage("Error updating staff account " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getStaffInfoById(Long staffId) {
        Response response = new Response();

        try {
            Staff staff = staffRepository.findById(staffId).orElseThrow(()-> new OurException("User Not Found"));
            StaffDTO staffDTO = Utils.mapStaffEntityToStaffDTO(staff);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setStaffDTO(staffDTO);
        } catch (OurException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting patient information " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getStaffInfoByEmail(String email) {
        Response response = new Response();

        try {
            Staff staff = staffRepository.findByEmail(email).orElseThrow(()-> new OurException("User Not Found"));
            StaffDTO staffDTO = Utils.mapStaffEntityToStaffDTO(staff);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setStaffDTO(staffDTO);
        } catch (OurException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting patient information " + e.getMessage());
        }

        return response;
    }
}
