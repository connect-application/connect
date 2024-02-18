package com.uwaterloo.connect.service;

import com.uwaterloo.connect.enums.UserRole;
import com.uwaterloo.connect.model.User;
import com.uwaterloo.connect.utils.InputValidator;
import com.uwaterloo.connect.dto.RegistrationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final UserService userService;

    public String register(RegistrationRequest request) {
        //TODO create custom exceptions
        if (!InputValidator.emailValidator(request.getEmail())) {
            throw new IllegalArgumentException("email not valid");
        }
        return userService.signUpUser(new User(request.getFirstName(), request.getLastName(), request.getUserName(), request.getEmail(), request.getPassword(), request.getDateOfBirth(), UserRole.USER));
    }
}
