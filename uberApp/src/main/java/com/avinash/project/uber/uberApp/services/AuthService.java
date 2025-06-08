package com.avinash.project.uber.uberApp.services;

import com.avinash.project.uber.uberApp.dto.DriverDto;
import com.avinash.project.uber.uberApp.dto.SignUpDto;
import com.avinash.project.uber.uberApp.dto.UserDto;

public interface AuthService {

    String[] login(String username, String password);

    UserDto signUp(SignUpDto signUpDto);

    DriverDto onBoardNewDriver(Long userId, String vehicleId);

    String refreshToken(String refreshToken);
}
