package com.uwaterloo.connect.service;

import dto.RegistrationRequest;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    public String register(RegistrationRequest request) {
        return "Registered";
    }
}
