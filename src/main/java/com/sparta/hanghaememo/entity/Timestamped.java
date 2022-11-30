package com.sparta.hanghaememo.entity;

import lombok.Getter; //Lombok이란 어노테이션 기반으로 코드를 자동완성 해주는 라이브러리이다.
                                     // Lombok을 이용하면 Getter, Setter, Equlas, ToString 등과 다양한 방면의 코드를 자동완성 시킬 수 있다.
import org.springframework.data.annotation.CreatedDate;  //@CreatedDate 생성 시 저절로 생김
import org.springframework.data.annotation.LastModifiedDate;  //@LastModifiedDate 생성 시 저절로 생김
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass  // 객체의 입장에서 공통 매핑 정보가 필요할 때 사용한다.
                                    //id, name은 객체의 입장에서 볼 때 계속 나온다.
                                    //이렇게 공통 매핑 정보가 필요할 때, 부모 클래스에 선언하고 속성만 상속 받아서 사용하고 싶을 때 @MappedSuperclass를 사용한다.
                                    //DB 테이블과는 상관없다. DB는 매핑 정보 다 따로 쓰고 있다. 객체의 입장이다.

@EntityListeners(AuditingEntityListener.class)  //EntityListeners = Entity가 삽입, 삭제 수정, 조회 등의 작업을 할 때 전, 후에 어떤 작업을 하기 위해
                                                                            //이번트 처리를 위한 어노테이션이다.
                                                                            //JPA Auditing을 사용하면 생성 시간과 수정 시간을 자동화 할 수 있다.
public class Timestamped {

    @CreatedDate //데이터 저장 시 '생성된 시간 정보'
    private LocalDateTime createdAt; // LocalDateTime = 날짜와 시간 정보 모두가 필요할 때 사용.

    @LastModifiedDate //데이터 저장 시 '수정된 시간 정보'
    private LocalDateTime modifiedAt; //LocalDateTime = 날짜와 시간 정보 모두가 필요할 때 사용.

}