package com.sparta.hanghaememo.dto;

import lombok.Getter;

@Getter
public class MemoDeleteResponseDto {
    private Boolean success;

    public MemoDeleteResponseDto(Boolean result) {
        this.success = result;
    }
}
