package com.uwaterloo.connect.service;

import com.uwaterloo.connect.dto.SignUpResponse;
import com.uwaterloo.connect.enums.UserRole;
import com.uwaterloo.connect.model.Token;
import com.uwaterloo.connect.model.User;
import com.uwaterloo.connect.utils.EmailBuilder;
import com.uwaterloo.connect.utils.InputValidator;
import com.uwaterloo.connect.dto.SignUpRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SignUpService {

    private final UserService userService;
    private final EmailSender emailSender;
    private final TokenService tokenService;

    public SignUpResponse signUp(SignUpRequest request) {
        //TODO create custom exceptions
        if (!InputValidator.emailValidator(request.getEmail())) {
            throw new IllegalArgumentException("email not valid");
        }
        String token = userService.signUpUser(new User(request.getFirstName(), request.getLastName(), request.getUserName(), request.getEmail(), request.getPassword(), request.getDateOfBirth(), UserRole.USER));
        String link = "http://localhost:8080/api/v1/signup/confirm?token=" + token;
        String mssg = "Thank you for signing up. Please click on the below link to activate your account:";
        emailSender.send(request.getEmail(), EmailBuilder.buildEmail(request.getFirstName(), link,mssg));

        return SignUpResponse.builder()
                .code("00")
                .email(request.getEmail())
                .status("Success!  Please, check your email for to complete your signup process")
                .build();
    }

    public String confirmEmail(String token) {
        Token dbToken = tokenService.confirmToken(token);
        userService.enableUser(dbToken.getUser().getEmail());
        return "Email verified successfully";
    }
}

