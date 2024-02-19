package com.uwaterloo.connect.controller;

import com.uwaterloo.connect.dto.LoginRequest;
import com.uwaterloo.connect.dto.LoginResponse;
import com.uwaterloo.connect.dto.SignUpRequest;
import com.uwaterloo.connect.model.User;
import com.uwaterloo.connect.service.LoginService;
import com.uwaterloo.connect.service.SignUpService;
import com.uwaterloo.connect.utils.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/login")
@AllArgsConstructor
public class LoginController {
    private final LoginService loginService;
    @PostMapping
    public LoginResponse login(@RequestBody LoginRequest request) {
        return loginService.login(request);
    }

}
