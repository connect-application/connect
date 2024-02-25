package com.uwaterloo.connect.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class SignUpRequest {
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String password;
    private LocalDate dateOfBirth;
}
