package com.sparta.hanghaememo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDto {
    private String msg;
    private Integer statusCode;
}