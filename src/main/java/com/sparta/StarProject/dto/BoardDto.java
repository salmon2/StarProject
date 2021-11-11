package com.sparta.StarProject.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
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
}
