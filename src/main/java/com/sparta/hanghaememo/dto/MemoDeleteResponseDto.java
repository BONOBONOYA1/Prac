package com.sparta.hanghaememo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemoDeleteResponseDto {
    private String msg;
    private Integer statusCode;

    public MemoDeleteResponseDto(String msg, Integer statusCode) {
        this.msg = msg;
        this.statusCode = statusCode;
    }
}

//  private Boolean success;
//
//    public MemoDeleteResponseDto(Boolean result) {
//        this.success = result;
//    }
//}