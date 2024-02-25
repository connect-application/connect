package com.uwaterloo.connect.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class LoginResponse {
    private String email;
    private String jwtToken;
    private String code;
    private String status;
}
