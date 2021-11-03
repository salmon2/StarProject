package com.sparta.StarProject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RecommendStarResponseDto {
    private String location;
    private Long starGazing;
    private Long avgTemperature;

    public RecommendStarResponseDto(String cityName, Long starGazing, Long temperature) {
        this.location = cityName;
        this.starGazing = starGazing;
        this.avgTemperature = temperature;
    }
}
