package com.sparta.hanghaememo.dto;

import com.sparta.hanghaememo.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductResponseDto {
    private Long id;
    private String title;


    public ProductResponseDto(Product product) {
        this.id = product.getId();
        this.title = product.getTitle();
    }
}