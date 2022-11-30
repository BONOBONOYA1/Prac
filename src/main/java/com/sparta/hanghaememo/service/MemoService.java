package com.sparta.hanghaememo.service;


import com.sparta.hanghaememo.dto.MemoRequestDto;
import com.sparta.hanghaememo.dto.MemoResponseDto;
import com.sparta.hanghaememo.entity.Memo;
import com.sparta.hanghaememo.repository.MemoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service //Service라는 것을 알려주기 위해 작성
@RequiredArgsConstructor //controller 자바클래스에 설명있음.
public class MemoService {
        private static final MemoRepository memoRepository = null; //MemoRepository를 사용할 수 있도록 작성. MemoRepository에 연결됨.

    @Transactional(readOnly = true)
    public MemoResponseDto getPost(Long id) {
        Memo memo = memoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        return new MemoResponseDto(memo);
    }

    //final 키워드가 붙어 있으면 값을 생성자에서 초기화 한 이후에 변경할 수 없습니다.
   @Transactional  //Transactional의 안정성을 보장해주는 어노테이션.안정성을 보장해주는 4가지 법칙.ACID(줄임말)가 깨지지않게 해줌.
    public Memo createMemo(MemoRequestDto requestDto) {
        Memo memo = new Memo(requestDto);
        memoRepository.save(memo);
        return memo;
    }

    @Transactional(readOnly = true) //읽기코드
    public List<Memo> getMemos() {
       return memoRepository.findAllByOrderByModifiedAtDesc(); //findAll을 하면 해당 테이블에 있는 모든걸 가져와야 하니까 update를 해야된다. memoRepository요게 인터페이스 repository인가?
                                                                                                            //memoRopository에서 findAllByOrderByModifiedAtDesc 코드 작성 후 service자바 클래스로 와서 findAll을 findAllByOrderByModifiedAtDesc로 바꿔줌
   }
   @Transactional
    public MemoResponseDto updateMemo(Long id, MemoRequestDto requestDto) {
        Memo memo = memoRepository.findByIdAndPassword(id, requestDto.getPassword()).orElseThrow(     //수정할 데이터베이스가 있는지 없는지 확인작업이 필요함.
                () -> new IllegalArgumentException("비밀번호가 틀립니다..")  //IllegalArgumentException 메서드가 허가되지 않거나 부적절한 argument를 받았을 경우에 던져지는/던질 수 있는 예외입니다.
       ); //orElseThrow예외 처리
        memo.update(requestDto);//가지고 온 메모에 있는 값을 변경해줄 코드. update메소드를 사용해서 변경. 변경된 값은 client 에서 보내준 rquestDto안에 들어가 있는 값으로 수정한다.
        memoRepository.save(memo);
       return new MemoResponseDto(memo); //update코드가 memo자바 클래스 안에 안 만들어져 있기에 memo자바 클래스 안에 만들러 가야함.
   }

   @Transactional
    public Long deleteMemo(Long id) {
       memoRepository.deleteById(id);
       return id;
    }

}