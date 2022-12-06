package com.sparta.hanghaememo.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Setter
@Getter
public class SignupRequestDto {
    @Pattern(regexp = [a-z] [0-9]);
    private String username;

    @Pattern(regexp = [A-Z] [0-9]);
    private String password;
}