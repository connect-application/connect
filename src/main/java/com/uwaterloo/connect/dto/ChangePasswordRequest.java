package com.uwaterloo.connect.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ChangePasswordRequest {
    private String email;
    private String password;
    private String newPassword;
}
