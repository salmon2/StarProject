package com.sparta.StarProject.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DetailWeatherList {
    private String time;
    private Long rainPercent;
    private String weather;
    private Long humidity;
    private Long temperature;
    private Long dust;

    @QueryProjection
    public DetailWeatherList(String time, Long rainPercent, String weather, Long humidity, Long temperature, Long dust) {
        this.time = time;
        this.rainPercent = rainPercent;
        this.weather = weather;
        this.humidity = humidity;
        this.temperature = temperature;
        this.dust = dust;
    }
}
