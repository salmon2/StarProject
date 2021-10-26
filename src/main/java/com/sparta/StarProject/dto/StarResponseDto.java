package com.sparta.StarProject.dto;

import com.sparta.StarProject.domain.Location;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StarResponseDto {
    private Long id;
    private String moonrise;
    private String moonset;
    private String visibility;
    private String humidity;
    private String weather;
    private String temperature;
    private Long StarGazing;
    private Location location;

    public StarResponseDto(Long id, String moonrise, String moonset, String visibility, String humidity, String weather, String temperature, Long starGazing, Location location){
        this.id = id;
        this.moonrise = moonrise;
        this.moonset = moonset;
        this.visibility = visibility;
        this.humidity = humidity;
        this.weather = weather;
        this.temperature = temperature;
        this.StarGazing = starGazing;
        this.location = location;

    }

}
