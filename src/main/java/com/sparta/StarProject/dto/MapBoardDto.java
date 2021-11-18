package com.sparta.StarProject.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MapBoardDto {
    private Long id;
    private String dtype;
    private String title;
    private Double x_location;
    private Double y_location;
    private String address;
    private Boolean bookmark;
    private Long starGazing;
    private String img;

    @QueryProjection
    public MapBoardDto(Long id, String dtype, String title, Double x_location, Double y_location, String address, Boolean bookmark, Long starGazing, String img) {
        this.id = id;
        this.dtype = dtype;
        this.title = title;
        this.x_location = x_location;
        this.y_location = y_location;
        this.address = address;
        this.bookmark = bookmark;
        this.starGazing = starGazing;
        this.img = img;
    }
}
