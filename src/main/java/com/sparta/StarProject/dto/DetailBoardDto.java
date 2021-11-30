package com.sparta.StarProject.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetailBoardDto {
    private Long id;
    private String date;
    private String writer;
    private String title;
    private String address;
    private String img;
    private String content;
    private Double x_location;
    private Double y_location;


    private Boolean likeCheck;
    private Long likeCount;
    private Boolean bookmarkCheck;

    private DetailWeatherCityInfoDto weather;

    @QueryProjection
    public DetailBoardDto(Long id, String date, String writer, String title, String address, String img, String content, Double x_location, Double y_location, Boolean likeCheck, Long likeCount, Boolean bookmarkCheck) {
        this.id = id;
        this.date = date;
        this.writer = writer;
        this.title = title;
        this.address = address;
        this.img = img;
        this.content = content;
        this.x_location = x_location;
        this.y_location = y_location;
        this.likeCheck = likeCheck;
        this.likeCount = likeCount;
        this.bookmarkCheck = bookmarkCheck;
    }
}
