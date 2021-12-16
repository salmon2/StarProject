package com.sparta.StarProject.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetailWeatherCityInfoDto {
    private String cityName;
    private String date;
    private String starGazing;
    private String moonrise;
    private String moonset;
    private List<DetailWeatherList> weatherList;

    @QueryProjection
    public DetailWeatherCityInfoDto(String cityName, String date, Long starGazing, String moonrise, String moonset) {
        String data = null;
        if(starGazing > 6)
            data = " (높음)";
        else if (starGazing >3)
            data = " (보통)";
        else if(starGazing >= 0)
            data = " (낮음)";

        this.cityName = cityName;
        if(cityName.equals("대구"))
            cityName = "대구 광역시";
        else if (cityName.equals("부산"))
            cityName = "부산 광역시";
        this.date = date;
        this.starGazing =starGazing.toString() +  data;
        this.moonrise = moonrise;
        this.moonset = moonset;
    }
}
