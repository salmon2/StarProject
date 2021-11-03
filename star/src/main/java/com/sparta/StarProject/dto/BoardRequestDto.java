package com.sparta.StarProject.dto;

import com.sparta.StarProject.domain.Location;
import com.sparta.StarProject.domain.board.Board;
import lombok.Getter;

@Getter
public class BoardRequestDto {
    private String title;
    private String img;
    private String contents;
    private Location location;

    public BoardRequestDto(String title, String img, String contents, Location location) {
        this.title = title;
        this.img = img;
        this.contents = contents;
        this.location = location;
    }
}
