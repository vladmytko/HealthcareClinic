package com.vladyslav.HealthcareClinic.service.impl;

import com.vladyslav.HealthcareClinic.dto.*;
import com.vladyslav.HealthcareClinic.dto.requests.LoginRequest;
import com.vladyslav.HealthcareClinic.dto.requests.RegisterRequest;
import com.vladyslav.HealthcareClinic.dto.response.Response;
import com.vladyslav.HealthcareClinic.entity.Patient;
import com.vladyslav.HealthcareClinic.entity.User;
import com.vladyslav.HealthcareClinic.exception.OurException;
import com.vladyslav.HealthcareClinic.repo.PatientRepository;
import com.vladyslav.HealthcareClinic.repo.UserRepository;
import com.vladyslav.HealthcareClinic.service.interfac.IUserService;
import com.vladyslav.HealthcareClinic.utils.JWTUtils;
import com.vladyslav.HealthcareClinic.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PatientService patientService;



    @Override
    public Response register(RegisterRequest registerRequest) {

        Response response = new Response();

        try {
            // Check if email is in use
            if (userRepository.existsByEmail(registerRequest.getEmail())) {
                throw new OurException(registerRequest.getEmail() + " already exists");
            }

            // Create a User entity
            User user = new User();
            user.setEmail(registerRequest.getEmail());
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            user.setRole("PATIENT");

            // Save the User entity
            User savedUser = userRepository.save(user);

            // Map User to User DTO
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(savedUser);

            // Create a Patient entity (diagnosis and condition remain null)
            Patient patient = new Patient();
            patient.setFirstName(registerRequest.getFirstName());
            patient.setLastName(registerRequest.getLastName());
            patient.setAddress(registerRequest.getAddress());
            patient.setEmail(registerRequest.getEmail());
            patient.setPhoneNumber(registerRequest.getPhoneNumber());
            patient.setDateOfBirth(registerRequest.getDateOfBirth());
            patient.setUser(savedUser);

            Response patientResponse = patientService.addPatient(patient);


            // Set success response
            response.setStatusCode(200);
            response.setMessage("Patient registered successfully");
            response.setUserDTO(userDTO);
            response.setPatientDTO(patientResponse.getPatientDTO());

        } catch (OurException e) {

            // Handle custom exception
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {

            // Handle unexpected exception
            response.setStatusCode(500);
            response.setMessage("Error occurred during user registration " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response login(LoginRequest loginRequest) {
        Response response = new Response();

        try{
            // Authenticate user credentials
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );

            // Fetch user from database
            var user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(()-> new OurException("User not found"));

            // Generate JWT token
            String token = jwtUtils.generateToken(user);

            // Set success response
            response.setStatusCode(200);
            response.setToken(token);
            response.setRole(user.getRole());
            response.setExpirationTime("7 days");
            response.setMessage("Login is successful");
        } catch (OurException e) {

            // Handle user not found
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {

            // Handle unexpected errors
            response.setStatusCode(500);
            response.setMessage("Error occurred during user login");
        }
        return response;
    }

    @Override
    public Response getAllUsers() {

        Response response = new Response();

        try {
            // Fetch all users from database
            List<User> userList = userRepository.findAll();

            // Map users to users DTO
            List<UserDTO> userDTOList = Utils.mapUserListEntityToUserListDTO(userList);

            // Set success response
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setUserList(userDTOList);
        } catch (OurException e) {

            // Handle custom error
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {

            // Handle unexpected error
            response.setStatusCode(500);
            response.setMessage("Error getting all users " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response deleteUser(Long userId) {

        Response response = new Response();

        try{
            // Check if user exists, throw OurException if not found
            userRepository.findById(userId).orElseThrow(() -> new OurException("User Not Found"));

            // Delete the user
            userRepository.deleteById(userId);

            // Set successes response
            response.setStatusCode(200);
            response.setMessage("Successful");
        } catch (OurException e) {

            // Handle custom exception for user not found
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {

            // Handle unexpected exceptions
            response.setStatusCode(500);
            response.setMessage("Error deleting user " + e.getMessage());

        }

        return response;
    }

    @Override
    public Response getUserById(Long userId) {

        Response response = new Response();

        try {
            // Fetch user from database
            User user = userRepository.findById(userId).orElseThrow(() -> new OurException("User not found"));

            // Map user entity to user DTO
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);

            // Set successes response
            response.setStatusCode(200);
            response.setMessage("Successful");
            response.setUserDTO(userDTO);
        } catch (OurException e) {

            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {

            response.setStatusCode(500);
            response.setMessage("Error getting user by id " + e.getMessage());

        }

        return response;
    }

    @Override
    public Response getUserInfo(String email) {

        Response response = new Response();

        try{
            User user = userRepository.findByEmail(email).orElseThrow(() -> new OurException("User Not Found"));
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setUserDTO(userDTO);

        } catch (OurException e) {

            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {

            response.setStatusCode(500);
            response.setMessage("Error getting user information " + e.getMessage());

        }

        return response;
    }

    @Override
    public Response createAdmin(String email, String password) {
        Response response = new Response();

        try {
            // Check if email is in use
            if (userRepository.existsByEmail(email)) {
                throw new OurException(email + " already exists");
            }

            // Create a User entity
            User user = new User();
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            user.setRole("ADMIN");

            // Save the User entity
            User savedUser = userRepository.save(user);

            // Map User to User DTO
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(savedUser);

            response.setStatusCode(201);
            response.setMessage("Patient registered successfully");
            response.setUserDTO(userDTO);

        } catch (OurException e){
            // Handle custom exception
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            // Handle unexpected exception
            response.setStatusCode(500);
            response.setMessage("Error occurred during user registration " + e.getMessage());
        }

        return response;
    }



}
