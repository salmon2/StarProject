package com.sparta.StarProject.service;

import com.sparta.StarProject.domain.Location;
import com.sparta.StarProject.domain.Star;
import com.sparta.StarProject.domain.Weather;

import com.sparta.StarProject.dto.RecommendStarResponseDto;
import com.sparta.StarProject.dto.StarInfoResponseDto;
import com.sparta.StarProject.dto.StarWeatherResponseDto;
import com.sparta.StarProject.repository.LocationRepository;
import com.sparta.StarProject.repository.StarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class StarService {

    private final LocationRepository locationRepository;
    private final StarRepository starRepository;



    public StarInfoResponseDto getStarInfo(Double latitude, Double longitude){

        Location findLocation = locationRepository.findByLongitudeAndLatitude(longitude, latitude);

        Star findStar = findLocation.getStar();

        StarInfoResponseDto starInfoResponseDto = new StarInfoResponseDto(
                findStar.getMoonrise(),
                findStar.getMoonSet(),
                findStar.getStarGazing()
        );
        return starInfoResponseDto;

    }



    public StarWeatherResponseDto getWeatherInfo(Double latitude, Double longitude, String predictTime) {

        Location location = locationRepository.findByLongitudeAndLatitude(longitude, latitude);
        List<Weather> weatherList = location.getWeatherList();
        Weather findWeather = null;

        for (Weather weather : weatherList) {
            if(weather.getPredictTime().equals(predictTime)){
                findWeather = weather;
            }
        }

        StarWeatherResponseDto starWeatherResponseDto = new StarWeatherResponseDto(
                location.getCityName(),
                findWeather.getRainPercent(),
                findWeather.getHumidity(),
                findWeather.getWeather(),
                findWeather.getTemperature(),
                findWeather.getMaxTemperature(),
                findWeather.getMinTemperature()
        );
        return starWeatherResponseDto;
    }


    public List<RecommendStarResponseDto> recommendStar() {
        List<RecommendStarResponseDto> recommendStarResponseDtos = new ArrayList<>();
        List<Star> hotList = starRepository.findTop3ByOrderByStarGazingDesc();

        for (Star star : hotList) {
            Location location = star.getLocation();

            List<Weather> weatherList = location.getWeatherList();
            Weather weather = weatherList.get(0);
            String maxTemperature = weather.getMaxTemperature();
            String minTemperature = weather.getMinTemperature();
            Double avg = (Double.valueOf(maxTemperature) + Double.valueOf(minTemperature))/2;


            RecommendStarResponseDto recommendStarResponseDto = new RecommendStarResponseDto(
                    location.getCityName(),
                    star.getStarGazing(),
                    avg.longValue()
            );

            recommendStarResponseDtos.add(recommendStarResponseDto);
        }

        return recommendStarResponseDtos;
    }
}
