package com.sparta.StarProject.dto;

import com.sparta.StarProject.domain.Location;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StarGuideResponseDto {
    private String moonrise;
    private String moonset;
    private String visibility;
    private String humidity;
    private String weather;
    private String temperature;

    public StarGuideResponseDto(String moonrise, String moonset, String visibility, String humidity, String weather, String temperature) {
        this.moonrise = moonrise;
        this.moonset = moonset;
        this.visibility = visibility;
        this.humidity = humidity;
        this.weather = weather;
        this.temperature = temperature;
    }
}
