package com.uwaterloo.connect.controller;

import com.uwaterloo.connect.dto.SignUpRequest;
import com.uwaterloo.connect.dto.SignUpResponse;
import com.uwaterloo.connect.service.SignUpService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/signup")
@AllArgsConstructor
public class SignUpController {
    private final SignUpService signUpService;
    @PostMapping
    public SignUpResponse signUp(@RequestBody SignUpRequest request) {
        return signUpService.signUp(request);
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return signUpService.confirmToken(token);
    }
}
