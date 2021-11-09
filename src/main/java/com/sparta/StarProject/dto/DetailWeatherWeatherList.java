package com.sparta.StarProject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetailWeatherWeatherList {
    private String time;
    private Long rainPercent;
    private String weather;
    private Long humidity;
    private Long temperature;
    private Long dust;
}
