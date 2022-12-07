package com.sparta.hanghaememo.dto;


import com.sparta.hanghaememo.entity.Memo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PostPutResponseDto {
    private Long id;
    private final LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String title;
    private String username;
    private String content;

    public PostPutResponseDto(Memo entity) { //?
        this.id = entity.getId();
        this.createdAt = entity.getCreatedAt();
        this.modifiedAt = entity.getModifiedAt();
        this.title = entity.getTitle();
        this.username = entity.getUsername();
        this.content = entity.getContent();
    }

}