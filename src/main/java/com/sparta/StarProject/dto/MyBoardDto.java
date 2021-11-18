package com.sparta.StarProject.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MyBoardDto {
    private Long id;
    private String title;
    private String content;
    private String img;

    @QueryProjection
    public MyBoardDto(Long id, String title, String content, String img) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.img = img;
    }
}
