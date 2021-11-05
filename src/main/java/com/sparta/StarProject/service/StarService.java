package com.sparta.StarProject.service;

import com.sparta.StarProject.domain.Location;
import com.sparta.StarProject.domain.Star;
import com.sparta.StarProject.domain.StarInfo;
import com.sparta.StarProject.domain.Weather;

import com.sparta.StarProject.domain.board.Timestamped;
import com.sparta.StarProject.dto.RecommendStarResponseDto;
import com.sparta.StarProject.dto.StarInfoResponseDto;
import com.sparta.StarProject.dto.StarPhotoDto;
import com.sparta.StarProject.dto.StarWeatherResponseDto;
import com.sparta.StarProject.repository.LocationRepository;
import com.sparta.StarProject.repository.StarInfoRepository;
import com.sparta.StarProject.repository.StarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class StarService {

    private final LocationRepository locationRepository;
    private final StarRepository starRepository;
    private final StarInfoRepository starInfoRepository;



    public StarInfoResponseDto getStarInfo(Double latitude, Double longitude){

//        //Location findLocation = locationRepository.findByLongitudeAndLatitude(longitude, latitude);
//
//        Star findStar = findLocation.getStar();
//
//        StarInfoResponseDto starInfoResponseDto = new StarInfoResponseDto(
//                findStar.getMoonrise(),
//                findStar.getMoonSet(),
//                findStar.getStarGazing()
//        );
        return null;

    }



    public StarWeatherResponseDto getWeatherInfo(Double latitude, Double longitude, String predictTime) {

//        Location location = locationRepository.findByLongitudeAndLatitude(longitude, latitude);
//        List<Weather> weatherList = location.getWeatherList();
//        Weather findWeather = null;
//
//        for (Weather weather : weatherList) {
//            if(weather.getPredictTime().equals(predictTime)){
//                findWeather = weather;
//            }
//        }
//
//        StarWeatherResponseDto starWeatherResponseDto = new StarWeatherResponseDto(
//                location.getCityName(),
//                findWeather.getRainPercent(),
//                findWeather.getHumidity(),
//                findWeather.getWeather(),
//                findWeather.getTemperature(),
//                findWeather.getMaxTemperature(),
//                findWeather.getMinTemperature()
//        );
        return null;
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

    public StarPhotoDto getStarPhoto() {
        List<String> currentTime = Timestamped.getCurrentTime();
        String month = currentTime.get(2);
        log.info("month = {}", month);

        Integer intMonth = Integer.valueOf(month);
        if(intMonth < 10){
            if(month.length() >= 2){
                month = month.substring(1);
            }
        }
        log.info("month = {}", month);
        StarInfo starInfo = starInfoRepository.findByMonth(month);
        StarPhotoDto starPhotoDto = new StarPhotoDto(
                starInfo.getStarImg(),
                starInfo.getStarName(),
                starInfo.getComment()
        );

        return starPhotoDto;
    }
}
