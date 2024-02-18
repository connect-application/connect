package com.uwaterloo.connect.controller;

import com.uwaterloo.connect.service.RegistrationService;
import dto.RegistrationRequest;
import lombok.AllArgsConstructor;
import org.hibernate.sql.results.spi.LoadContexts;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/registration")
@AllArgsConstructor
public class RegistrationController {
    // TODO remove this test method
    @GetMapping("/")
    public String test(){
        return "Register";
    }


    public String register(@RequestBody RegistrationRequest request){
        RegistrationService registerationService = null;
        return registerationService.register(request);
    }
}
