package com.sparta.hanghaememo.controller;

import com.sparta.hanghaememo.dto.LoginRequestDto;
import com.sparta.hanghaememo.dto.LoginResponseDto;
import com.sparta.hanghaememo.dto.SignupRequestDto;
import com.sparta.hanghaememo.dto.SignupResponseDto;
import com.sparta.hanghaememo.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService; //의존성 주입

    @PostMapping("/api/auth/signup") //회원가입 구현
    public SignupResponseDto signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);
        return new SignupResponseDto("회원가입 성공",200);
    }

    @ResponseBody
    @PostMapping("/api/auth/login") //로그인 구현
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) { //@RequestBody:ajax에서 body값이 넘어가기 때문에 써줌, HttpServletResponse: HttpRequest에서 Header가 넘어와 받아오는 것처럼 우리도 Client쪽으로 반환 할 때는 이렇게 Response객체를 반환한다.
        userService.login(loginRequestDto, response);
        return new LoginResponseDto("로그인 성공", 200 );
    }
}