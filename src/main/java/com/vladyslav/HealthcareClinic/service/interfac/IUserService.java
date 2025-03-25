package com.vladyslav.HealthcareClinic.service.interfac;

import com.vladyslav.HealthcareClinic.dto.requests.LoginRequest;
import com.vladyslav.HealthcareClinic.dto.requests.RegisterRequest;
import com.vladyslav.HealthcareClinic.dto.response.Response;

public interface IUserService {

    Response register(RegisterRequest registerRequest);

    Response login(LoginRequest loginRequest);

    Response getAllUsers();

    Response deleteUser(Long userId);

    Response getUserById(Long userId);

    Response getUserInfo(String email);

    Response createAdmin(String email, String password);


}
