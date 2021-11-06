package com.sparta.StarProject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RecommendStarResponseDto {
    private String cityName;
    private Long starGazing;
    private String temperature;

    public RecommendStarResponseDto(String cityName, Long starGazing, String temperature) {
        this.cityName = cityName;
        this.starGazing = starGazing;
        this.temperature = temperature;
    }
}
