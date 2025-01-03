package com.arun.project.uber.uberApp.services;

import com.arun.project.uber.uberApp.dto.DriverDto;
import com.arun.project.uber.uberApp.dto.SignupDto;
import com.arun.project.uber.uberApp.dto.UserDto;

public interface AuthService {

    UserDto signUp(SignupDto signupDto);

    DriverDto onBoardNewDriver(Long userId);
}
