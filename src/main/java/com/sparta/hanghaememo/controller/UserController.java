package com.sparta.hanghaememo.controller;

import com.sparta.hanghaememo.dto.LoginRequestDto;
import com.sparta.hanghaememo.dto.SignupRequestDto;
import com.sparta.hanghaememo.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService; //의존성 주입

    @GetMapping("/signup") //회원가입 페이지
    public ModelAndView signupPage() {
        return new ModelAndView("signup");
    }

    @GetMapping("/login") //로그인 페이지
    public ModelAndView loginPage() {
        return new ModelAndView("login");
    }

    @PostMapping("/signup") //회원가입 구현
    public String signup(SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);
        return "redirect:/api/user/login";
    }

    @ResponseBody
    @PostMapping("/login") //로그인 구현
    public String login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) { //@RequestBody:ajax에서 body값이 넘어가기 때문에 써줌, HttpServletResponse: HttpRequest에서 Header가 넘어와 받아오는 것처럼 우리도 Client쪽으로 반환 할 때는 이렇게 Response객체를 반환한다.
        userService.login(loginRequestDto, response);
        return "success";
    }
}