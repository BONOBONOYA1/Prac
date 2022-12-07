package com.sparta.hanghaememo.repository;

import org.springframework.data.jpa.domain.AbstractPersistable;
import com.sparta.hanghaememo.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.metamodel.SingularAttribute;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Repository
public interface MemoRepository extends JpaRepository<Memo, Long>{
    List<Memo> findAllByOrderByCreatedAtDesc();//수정된 시간이 가장 최근인 순서대로 가지고오게 하는 코드, Desc = 내림차순 코드


//    Optional<Memo> findByIdAndPassword(Long id, String password);
//    Boolean existsByIdAndPassword(Long id, String password);  //?
    }
