package com.sparta.StarProject.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
public class BoardDto {
    private String address;
    private String title;
    private String content;
    private String img;

    public BoardDto(String title, String content, String img) {
        this.title = title;
        this.content = content;
        this.img = img;
    }

    @QueryProjection
    public BoardDto(String address, String title, String content, String img) {
        this.address = address;
        this.title = title;
        this.content = content;
        this.img = img;
    }

}
