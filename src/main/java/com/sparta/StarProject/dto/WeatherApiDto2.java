package com.sparta.StarProject.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WeatherApiDto2 {
    private String baseDate;
    private String fcstTime;

    private String temperature; //기온
    private String weather;     //날씨
    private String maxTemperature;//최고 기온
    private String minTemperature;//최저 기온
    private String precipitationProbability;//강수확률
    private String humidity;    //습도
}
