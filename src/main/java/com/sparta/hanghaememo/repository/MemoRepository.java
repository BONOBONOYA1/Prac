package com.sparta.hanghaememo.repository;


import com.sparta.hanghaememo.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MemoRepository extends JpaRepository<Memo, Long>{
    List<Memo> findAllByOrderByModifiedAtDesc(); //수정된 시간이 가장 최근인 순서대로 가지고오게 하는 코드, Desc = 내림차순 코드
}
