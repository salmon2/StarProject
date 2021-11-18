package com.sparta.StarProject.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MyBookmarkListDto {
    private Long id;
    private String title;
    private String contents;
    private String img;

    @QueryProjection
    public MyBookmarkListDto(Long id, String title, String contents, String img) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.img = img;
    }
}
