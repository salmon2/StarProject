package com.sparta.StarProject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MapBoardDto {
    private Long id;
    private String dtype;
    private String title;
    private Double x_location;
    private Double y_location;
    private String address;
    private Long starGazing;
    private String img;
}
