package com.sparta.StarProject.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Weather {
    @Id @GeneratedValue
    private Long id;

    private String rainPercent; //강수확률
    private String humidity;    //습도
    private String weather;     //날씨
    private String temperature; //온도
    private String maxTemperature; //최고온도
    private String minTemperature; //최고온도
    private String predictTime; //예보시간  // 1500
    private String dust;    //미세먼지 농도
    private String date;    //20211029

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    public Weather(String humidity, String weather, String temperature, String maxTemperature, String minTemperature, String rainPercent, String predictTime, String dust, String date, Location location) {
        this.humidity = humidity;
        this.weather = weather;
        this.temperature = temperature;
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;
        this.rainPercent = rainPercent;
        this.predictTime = predictTime;
        this.dust = dust;
        this.date = date;
        this.location = location;
    }
}
