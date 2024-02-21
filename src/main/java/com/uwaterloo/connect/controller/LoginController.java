package com.uwaterloo.connect.controller;

import com.uwaterloo.connect.dto.*;
import com.uwaterloo.connect.service.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/login")
@AllArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping
    public LoginResponse login(@RequestBody LoginRequest request) {
        return loginService.login(request);
    }

    @PostMapping("/reset/token")
    public ResetPasswordTokenResponse resetPasswordToken(@RequestBody ResetPasswordTokenRequest request) {
        return loginService.resetPasswordToken(request);
    }

    @PostMapping("/reset")
    public ResetPasswordResponse resetPasswordToken(@RequestBody ResetPasswordRequest request) {
        return loginService.resetPassword(request);
    }

    @PostMapping("/change")
    public ChangePasswordResponse changePassword(@RequestBody ChangePasswordRequest request) {
        return loginService.changePassword(request);
    }

}
