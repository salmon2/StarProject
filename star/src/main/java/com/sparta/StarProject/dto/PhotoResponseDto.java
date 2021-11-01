package com.sparta.StarProject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PhotoResponseDto {
    private String starImg;
    private String starName;
    private String comment;

    public PhotoResponseDto(String starImg, String starName, String comment) {
        this.starImg = starImg;
        this.starName = starName;
        this.comment = comment;
    }
}
