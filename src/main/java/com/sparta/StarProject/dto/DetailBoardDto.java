package com.sparta.StarProject.dto;

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
    private String writer;
    private String title;
    private String address;
    private String img;
    private String content;
    private Double x_location;
    private Double y_location;
    private DetailWeatherDto weather;
}
