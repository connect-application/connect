package com.uwaterloo.connect.controller;

import com.uwaterloo.connect.model.User;
import com.uwaterloo.connect.repository.UserRepository;
import com.uwaterloo.connect.service.RegistrationService;
import dto.RegistrationRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/registration")
@AllArgsConstructor
public class RegistrationController {
    private RegistrationService registrationService;
    // TODO remove this test method
    private UserRepository userRepository;
    @PostMapping("/test")
    public Optional<User> test(@RequestBody RegistrationRequest request) {
//        return userRepository.findByEmail(request.getEmail());
        return userRepository.findByUsernameOrEmail(request.getUserName(), request.getEmail());
    }

    @PostMapping
    public String register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }
}
