package com.sparta.hanghaememo.dto;

import lombok.Getter;

@Getter
public class MemoRequestDto {
        //        private String username; //username과 contents를 받아와야 한다.
        private String content;   //Client에서 넘어오는 username과 contents를 이 객체를 통해서 받는다 >>controller로 가면 MemoRequestDto 빨간줄이 사라짐
        private String title;
}