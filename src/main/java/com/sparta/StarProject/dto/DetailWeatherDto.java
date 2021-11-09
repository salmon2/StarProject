package com.sparta.StarProject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetailWeatherDto {
    private String cityName;
    private String date;
    private Long starGazing;
    private String moonrise;
    private String moonset;
    private List<DetailWeatherWeatherList> weatherList;

}
