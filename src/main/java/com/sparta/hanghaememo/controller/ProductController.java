package com.sparta.hanghaememo.controller;

import com.sparta.hanghaememo.dto.ProductRequestDto;
import com.sparta.hanghaememo.dto.ProductResponseDto;
import com.sparta.hanghaememo.entity.Product;
import com.sparta.hanghaememo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // 관심 상품 등록하기
    @PostMapping("/products")
    public ProductResponseDto createProduct(@RequestBody ProductRequestDto requestDto, HttpServletRequest request) {
        // 응답 보내기
        return productService.createProduct(requestDto, request);
    }

    // 관심 상품 조회하기
    @GetMapping("/products")
    public Page<Product> getProducts(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("isAsc") boolean isAsc,
            HttpServletRequest request
    ) {
        // 응답 보내기
        return productService.getProducts(request, page-1, size, sortBy, isAsc);
    }


}