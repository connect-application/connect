package com.uwaterloo.connect.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private LocalDate dateOfBirth;
    private String about;
    private byte[] profilePic;
}