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
    private String locationName;
    private String address;
    private String img;
    private String content;
    private Double location_x;
    private Double location_y;
}
