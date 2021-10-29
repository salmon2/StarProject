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
public class LocationStarMoonDustDto {
    List<StarGazingDto> starGazing;
    SunMoonDto moon;
    List<WeatherApiDto2> weather;
    DustApiDto dust;
    String address;
}
