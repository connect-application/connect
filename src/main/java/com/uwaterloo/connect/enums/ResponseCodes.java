package com.uwaterloo.connect.enums;

import lombok.Getter;

@Getter
public enum ResponseCodes {
    SUCCESS("00");

    private final String code;
    ResponseCodes(String code) {
        this.code = code;
    }

}
