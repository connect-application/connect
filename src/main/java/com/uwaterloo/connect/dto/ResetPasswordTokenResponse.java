package com.uwaterloo.connect.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class ResetPasswordTokenResponse {
    private String email;
    private String code;
    private String status;
}
