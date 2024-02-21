package com.uwaterloo.connect.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Builder
public class ResetPasswordResponse {
    private String email;
    private String code;
    private String status;
}
