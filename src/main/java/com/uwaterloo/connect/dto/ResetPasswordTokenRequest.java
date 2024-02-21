package com.uwaterloo.connect.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class ResetPasswordTokenRequest {
    private String email;
}
