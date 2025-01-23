package com.vladyslav.HealthcareClinic.service.interfac;

import com.vladyslav.HealthcareClinic.dto.LoginRequest;
import com.vladyslav.HealthcareClinic.dto.RegisterRequest;
import com.vladyslav.HealthcareClinic.dto.Response;
import com.vladyslav.HealthcareClinic.entity.User;

public interface IUserService {

    Response register(RegisterRequest registerRequest);

    Response login(LoginRequest loginRequest);

    Response getAllUsers();

    Response deleteUser(Long userId);

    Response getUserById(Long userId);

    Response getUserInfo(String email);

    Response createAdmin(String email, String password);


}
