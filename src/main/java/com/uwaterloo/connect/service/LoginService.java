package com.uwaterloo.connect.service;

import com.uwaterloo.connect.dto.LoginRequest;
import com.uwaterloo.connect.dto.LoginResponse;
import com.uwaterloo.connect.model.User;
import com.uwaterloo.connect.utils.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LoginService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public LoginResponse login(@RequestBody LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();
        String jwtToken = jwtUtils.generateTokenFromUsername(user.getUsername());
        List<String> roles = user.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());

        return LoginResponse.builder()
                .email(user.getEmail())
                .jwtToken(jwtToken)
                .code("00")
                .status("Success")
                .build();
    }

}


