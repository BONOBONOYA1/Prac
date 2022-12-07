package com.sparta.hanghaememo.service;


import com.sparta.hanghaememo.Jwt.JwtUtil;
import com.sparta.hanghaememo.dto.MemoDeleteResponseDto;
import com.sparta.hanghaememo.dto.MemoRequestDto;
import com.sparta.hanghaememo.dto.MemoResponseDto;
import com.sparta.hanghaememo.entity.Memo;
import com.sparta.hanghaememo.entity.User;
import com.sparta.hanghaememo.repository.MemoRepository;
import com.sparta.hanghaememo.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service //Service라는 것을 알려주기 위해 작성
@RequiredArgsConstructor //controller 자바클래스에 설명있음.
public class MemoService {
    private final MemoRepository memoRepository; //MemoRepository를 사용할 수 있도록 작성. MemoRepository에 연결됨.
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional(readOnly = true)
    public MemoResponseDto getPost(Long id) {
        Memo memo = memoRepository.findById(id).orElseThrow(//memoRepository DB에서 가져온다.
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        return new MemoResponseDto(memo);
    }

    //final 키워드가 붙어 있으면 값을 생성자에서 초기화 한 이후에 변경할 수 없습니다.
    @Transactional  //Transactional의 안정성을 보장해주는 어노테이션.안정성을 보장해주는 4가지 법칙.ACID(줄임말)가 깨지지않게 해줌.
    public MemoResponseDto createMemo(MemoRequestDto requestDto, HttpServletRequest request) { //게시글 작성 API
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request); //resolveToken:토큰을 header에서 가져온다.
        Claims claims; //JWT안에 들어있는 정보들을 담을 수 있는 객체

        // 토큰이 있는 경우에만 관심상품 추가 가능
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            // 요청받은 DTO 로 DB에 저장할 객체 만들기
            Memo memo = memoRepository.saveAndFlush(new Memo(requestDto, user));

            return new MemoResponseDto(memo);
        } else {
            return null;
        }
    }


    @Transactional(readOnly = true) //전체 글 읽기코드
    public List<Memo> getMemos() {
        return memoRepository.findAllByOrderByCreatedAtDesc();          //findAll을 하면 해당 테이블에 있는 모든걸 가져와야 하니까 update를 해야된다. memoRepository요게 인터페이스 repository인가?
        //memoRopository에서 findAllByOrderByModifiedAtDesc 코드 작성 후 service자바 클래스로 와서 findAll을 findAllByOrderByModifiedAtDesc로 바꿔줌
    }

    @Transactional

    public MemoResponseDto updateMemo(Long id, MemoRequestDto requestDto, HttpServletRequest request) { //선택한 게시글 수정 API
        String token = jwtUtil.resolveToken(request); //resolveToken:토큰을 header에서 가져온다.
        Claims claims; //JWT안에 들어있는 정보들을 담을 수 있는 객체

        // 토큰이 있는 경우에만 관심상품 추가 가능
        if (token != null) {
            Memo memo = null;
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
                String username = claims.getSubject(); //토큰  안에 있는 username을 가져온 것.

                memo = memoRepository.findById(id).orElseThrow(//memoRepository (memo Entity)DB에서 가져온다.
                        () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
                );
                if (username.equals(memo.getUsername())) {
                    //update 진행
                    memo.update(requestDto); //
                } else {
                    //에러 알람.
                    throw new IllegalArgumentException("아이디가 다릅니다.");
                }
            }
            return new MemoResponseDto(memo);
        } else {
            throw new IllegalArgumentException("Token Error");
        }


//
//           유효한 토큰이면서 해당 사용자가 작성한 게시글만 수정 가능
//        Memo memo = memoRepository.findByIdAndPassword(id, requestDto.getPassword()).orElseThrow(     //수정할 데이터베이스가 있는지 없는지 확인작업이 필요함.
//                () -> new IllegalArgumentException("비밀번호가 틀립니다..")  //IllegalArgumentException 메서드가 허가되지 않거나 부적절한 argument를 받았을 경우에 던져지는/던질 수 있는 예외입니다.
//       ); orElseThrow예외 처리
//        memo.update(requestDto);//가지고 온 메모에 있는 값을 변경해줄 코드. update메소드를 사용해서 변경. 변경된 값은 client 에서 보내준 rquestDto안에 들어가 있는 값으로 수정한다.
//        memoRepository.save(memo);
//       return new MemoResponseDto(memo); //update코드가 memo자바 클래스 안에 안 만들어져 있기에 memo자바 클래스 안에 만들러 가야함.
    }

    @Transactional
    public MemoDeleteResponseDto deleteMemo(Long id, HttpServletRequest request) { //선택한 게시글 삭제 API
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 관심상품 최저가 업데이트 가능
        if (token != null) {
            // Token 검증
            Memo memo = null;
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
                String username = claims.getSubject(); //토큰  안에 있는 username을 가져온 것.

                memo = memoRepository.findById(id).orElseThrow(//memoRepository (memo Entity)DB에서 가져온다.
                        () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
                );
                if (username.equals(memo.getUsername())) {
                    //update 진행
                    memoRepository.deleteById(id);//
                } else {
                    //에러 알람.
                    throw new IllegalArgumentException("아이디가 다릅니다.");
                }
            }
            return new MemoDeleteResponseDto("게시글 삭제 성공", 200);

        } else {
            throw new IllegalArgumentException("Token Error");
        }

//            @Transactional
//            public boolean deleteMemo(Long id, String password) {
//                if(memoRepository.existsByIdAndPassword(id, password)) {
//                    memoRepository.deleteById(id);
//                    return true;
//
//                }
//                return false;
//            }
    }
}