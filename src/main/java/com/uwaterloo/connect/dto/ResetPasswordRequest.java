package com.uwaterloo.connect.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class ResetPasswordRequest {
    private String email;
    private String newPassword;
    private String token;
}
