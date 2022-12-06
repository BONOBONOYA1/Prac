package com.sparta.hanghaememo.entity;

import com.sparta.hanghaememo.dto.ProductRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity // DB 테이블 역할을 합니다.
@NoArgsConstructor
public class Product extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID가 자동으로 생성 및 증가합니다.
    private Long id;

    @Column(nullable = false)
    private String title;


    @Column(nullable = false)
    private Long userId; //JWT를 사용하여 관심상품 조회하기


    public Product(ProductRequestDto requestDto, Long userId) {
        this.title = requestDto.getTitle();
        this.userId = userId; //JWT를 사용하여 관심상품 조회하기
    }

}