package com.sparta.hanghaememo.controller;

import com.sparta.hanghaememo.Jwt.JwtUtil;
import com.sparta.hanghaememo.dto.MemoDeleteResponseDto;
import com.sparta.hanghaememo.dto.MemoRequestDto;
import com.sparta.hanghaememo.dto.MemoResponseDto;
import com.sparta.hanghaememo.entity.Memo;
import com.sparta.hanghaememo.entity.User;
import com.sparta.hanghaememo.repository.MemoRepository;
import com.sparta.hanghaememo.repository.UserRepository;
import com.sparta.hanghaememo.service.MemoService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController //@Controller에 @ResponseBody가 결합된 어노테이션입니다. @ResponseBody 리턴 타입이 HTTP의 응답 메시지로 전송
                            //컨트롤러 클래스에 @RestController를 붙이면,
                             //컨트롤러 클래스 하위 메서드에 @ResponseBody 어노테이션을 붙이지 않아도 문자열과 JSON 등을 전송할 수 있습니다.

@RequiredArgsConstructor //@RequiredArgsConstructor는 final 혹은 @NotNull이 붙은 필드의 생성자를 자동으로 만들어준다.
                                            // 이를 통해 새로운 필드를 추가할 때 다시 생성자를 만들거나 하는 등의 번거로움을 없앨 수 있다.
                                            //하지만 자동적으로 생성자가 만들어지기 때문에 내가 예상하지 못한 결과나 오류가 발생할 수 있기 때문에 그런 점도 염두해둬야 한다.
                                            // @RequiredArgsConstructor 어노테이션을 사용하면 간단한 방법으로 생성자 주입을 해줄 수 있다.
@Service
public class MemoController {
        private final MemoService memoService;
        private final UserRepository userRepository;

        private final MemoRepository memoRepository;
        private final JwtUtil jwtUtil;

        @GetMapping("/")  //클라이언트가 데이터를 요청해서 필요한 데이터를 받는 동작 @GetMapping
        public ModelAndView home() { //ModelAndView =데이터와 이동하고자 하는 View Page를 같이 저장한다
            return new ModelAndView("index"); //templates에 반환할 html파일 이름을 명시해주면 index.html을 반환해준다.
        }


        @PostMapping("/api/post") //id생성, index파일 writePost에서 Post 방식 url "/api/memos" 부분을 받는 서버부분을 개발
        public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto, HttpServletRequest request) {  //Memo를 바로 반환할 것이기에 Memo라고 해줍니다.
                                                                                                                                    // 객체 형식으로 넘어오기 때문에@RequestBody를 사용
                                                                                                                                    //Post 방식이기 때문에 body가 존재하고 우리가 원하는 저장해야 되는 것들이 넘어오기 때문에 @RequestBody를 사용할 것이고, MemoRequestDto를 객체로 받을 것이다.
                return memoService.createMemo(requestDto, request); //memoService의 creatMemo라는 연결되는 부분을 method로 만들어주고  parameter값으로  클라이언트에서 가져 온 requestDto 안에 들어있는 값들을 사용해야 돼서 만들고 createMemo함수를 만들러 간다.memoService로 고고
        }

        @GetMapping("api/post") //게시글 전체 조회, index파일 getMessages()에서 "GET"방식 "/api/memos"을 가져온 것임.
        public List<Memo> getMemos() { //List형식으로 반환타입 해줌, Client에서 전달해주는 데이터가 없기에 parameter는 넣을 필요가 없다.
                return memoService.getMemos(); //memoService에 연결을 해서 getMemos()연결하는 메서드를 만들어야함 getMemos빨간줄 누르면 service로 넘어가서 자동생성됨.
        }

        @GetMapping("/api/post/{id}")  // 선택한 게시글 조회
        public MemoResponseDto getPost(@PathVariable Long id) { //@PathVariable => {id}에 들어오는 값을 Long id에 담아줌 (받을데이터가 1개일때),  url뒤에 들어오는 데이터들의 위치를 통해서 객체의 위치와 똑같이 써줌으로서 Mapping을 시켜준다.
                return memoService.getPost(id);
        }

        @PutMapping("/api/post/{id}") //선택한 게시글 수정, index파일 submitEdit()에서 "PUT"방식 "/api/memos"을 가져온 것임.{id}추가 입력
        public MemoResponseDto updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto, HttpServletRequest request) {  //@requestBody => json 형식 (받을 데이터가 여러개일때)
                return memoService.updateMemo(id, requestDto, request);//memoService에 연결을 해서 update()연결하는 메서드를 만들어야함.

        }

        @DeleteMapping("/api/post/{id}")  //선택한 게시글 삭제, index파일 deleteOne()에서 "DELETE"방식 "/api/memos"을 가져온 것임.{id}추가 입력
        public MemoDeleteResponseDto deleteMemo(@PathVariable Long id,  HttpServletRequest request) {  //위와 같은 방식으로 service 자바클래스로 가서 만들어준다.
            return  memoService.deleteMemo(id, request);

        }

//        @DeleteMapping("/api/post/{id}")  //선택한 게시글 삭제, index파일 deleteOne()에서 "DELETE"방식 "/api/memos"을 가져온 것임.{id}추가 입력
//        public MemoDeleteResponseDto deleteMemo(@PathVariable Long id, @RequestBody MemoDeleteRequestDto requestDto) {  //위와 같은 방식으로 service 자바클래스로 가서 만들어준다.
//                boolean deleteResult = memoService.deleteMemo(id, requestDto.getPassword());
//                return new MemoDeleteResponseDto(deleteResult);
//        }
}