package com.uwaterloo.connect.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class LoginResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private LocalDate dateOfBirth;
    private String jwtToken;
    private String code;
    private String status;
}
