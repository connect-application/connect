package com.uwaterloo.connect.service;

import com.uwaterloo.connect.dto.*;
import com.uwaterloo.connect.enums.ResponseCodes;
import com.uwaterloo.connect.model.Token;
import com.uwaterloo.connect.model.User;
import com.uwaterloo.connect.repository.UserRepository;
import com.uwaterloo.connect.utils.EmailBuilder;
import com.uwaterloo.connect.utils.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class LoginService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserService userService;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final EmailSender emailSender;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public LoginResponse login(@RequestBody LoginRequest request) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();
        String jwtToken = jwtUtils.generateTokenFromUsername(user.getUsername());
        return LoginResponse.builder()
                .email(user.getEmail())
                .jwtToken(jwtToken)
                .code(ResponseCodes.SUCCESS.getCode())
                .status("Login Successful")
                .build();
    }

    public ResetPasswordTokenResponse resetPasswordToken(ResetPasswordTokenRequest request) {
        User user = (User) userService.loadUserByUsername(request.getEmail());
        String token = UUID.randomUUID().toString();
        // creating token with 15 mins expiry
        Token dbToken = new Token(token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), user);
        tokenService.saveToken(dbToken);
        // TODO change to frontend url
        StringBuilder urlBuilder = new StringBuilder("http://localhost:3000/reset-password");
        urlBuilder.append("?token=").append(token);
        urlBuilder.append("&email=").append(request.getEmail());
        String mssg = "Please click on the below link to reset your password:";
        emailSender.send(request.getEmail(), EmailBuilder.buildEmail(user.getFirstName(), urlBuilder.toString(), mssg));
        return ResetPasswordTokenResponse.builder()
                .email(request.getEmail())
                .code(ResponseCodes.SUCCESS.getCode())
                .status("Reset email sent successfully")
                .build();
    }

    public ResetPasswordResponse resetPassword(ResetPasswordRequest request) {
        tokenService.confirmToken(request.getToken());
        updatePassword(request.getEmail(), request.getNewPassword());
        return ResetPasswordResponse.builder()
                .email(request.getEmail())
                .code(ResponseCodes.SUCCESS.getCode())
                .status("Password reset successfully")
                .build();
    }

    public ChangePasswordResponse changePassword(ChangePasswordRequest request) {
        updatePassword(request.getEmail(), request.getNewPassword());
        return ChangePasswordResponse.builder()
                .email(request.getEmail())
                .code(ResponseCodes.SUCCESS.getCode())
                .status("Password changed successfully")
                .build();
    }

    public User updatePassword(String email, String newPassword) {
        User user = (User) userService.loadUserByUsername(email);
        String encodedPassword = bCryptPasswordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }
}
