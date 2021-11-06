package com.sparta.StarProject.dto;

import com.sparta.StarProject.domain.Location;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StarWeatherResponseDto {
    private String cityName;
    private String rainPercent; //강수확률
    private String humidity;    //습도
    private String weather;     //날씨
    private String temperature; //온도
    private String maxTemperature; //최고온도
    private String minTemperature; //최고온도
    private String dust;    //미세먼지

    public StarWeatherResponseDto(String cityName, String rainPercent, String humidity, String weather, String temperature, String maxTemperature, String minTemperature, String dust) {
        this.cityName = cityName;
        this.rainPercent = rainPercent;
        this.humidity = humidity;
        this.weather = weather;
        this.temperature = temperature;
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;
        this.dust = dust;
    }
}
