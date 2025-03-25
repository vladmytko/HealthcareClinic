package com.vladyslav.HealthcareClinic.controller;

import com.vladyslav.HealthcareClinic.dto.requests.LoginRequest;
import com.vladyslav.HealthcareClinic.dto.requests.RegisterRequest;
import com.vladyslav.HealthcareClinic.dto.response.Response;
import com.vladyslav.HealthcareClinic.service.interfac.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<Response> register (@Valid @RequestBody RegisterRequest registerRequest) {
        Response response = userService.register(registerRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login (@RequestBody LoginRequest loginRequest){
        Response response = userService.login(loginRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


    @PostMapping("/register-admin")
    public ResponseEntity<Response> registerAdmin(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");

        Response response = userService.createAdmin(email, password);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}
