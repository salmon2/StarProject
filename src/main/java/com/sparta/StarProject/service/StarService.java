package com.sparta.StarProject.service;

import com.sparta.StarProject.api.API;
import com.sparta.StarProject.api.locationAPI.GpsToAddress;
import com.sparta.StarProject.domain.Location;
import com.sparta.StarProject.domain.Star;
import com.sparta.StarProject.domain.StarInfo;
import com.sparta.StarProject.domain.Weather;

import com.sparta.StarProject.domain.board.Timestamped;
import com.sparta.StarProject.dto.*;
import com.sparta.StarProject.exception.ErrorCode;
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
    private final GpsToAddress gpsToAddress;
    private final API api;


    public StarInfoResponseDto getStarInfo(double latitude, double longitude) throws Exception{
        try{
            String address = gpsToAddress.getAddress(latitude,longitude);
            List<String> cityName = api.processAddress(address);

            Location findLocation = locationRepository.findByCityName(cityName.get(0));

            Star findStar = findLocation.getStar();

            StarInfoResponseDto starInfoResponseDto = new StarInfoResponseDto(
                    findStar.getMoonrise(),
                    findStar.getMoonSet(),
                    findStar.getStarGazing()
            );
            return starInfoResponseDto;
        }
        catch(NullPointerException nullPointerException){
            throw new NullPointerException(ErrorCode.NotFoundBoard.getMessage());
        }

    }

    public StarWeatherResponseDto getWeatherInfo(Double latitude, Double longitude, String predictTime) throws Exception {
        try {
            String address = gpsToAddress.getAddress(latitude, longitude);
            List<String> cityName = api.processAddress(address);
            Location findLocation = locationRepository.findByCityName(cityName.get(0));

            List<Weather> weatherList = findLocation.getWeatherList();
            Weather findWeather = null;

            for (Weather weather : weatherList) {
                if (weather.getPredictTime().equals(predictTime)) {
                    findWeather = weather;
                }
            }

            StarWeatherResponseDto starWeatherResponseDto = new StarWeatherResponseDto(
                    findLocation.getCityName(),
                    findWeather.getRainPercent(),
                    findWeather.getHumidity(),
                    findWeather.getWeather(),
                    findWeather.getTemperature(),
                    findWeather.getMaxTemperature(),
                    findWeather.getMinTemperature(),
                    findWeather.getDust()
            );

            return starWeatherResponseDto;
        }
        catch(NullPointerException nullPointerException){
            throw new NullPointerException(ErrorCode.NotFoundBoard.getMessage());
        }
    }


    public starHotDto recommendStar() {
        List<RecommendStarResponseDto> recommendStarResponseDtos = new ArrayList<>();
        List<Star> hotList = starRepository.findTop3ByOrderByStarGazingDesc();

        for (Star star : hotList) {
            Location location = star.getLocation();

            List<Weather> weatherList = location.getWeatherList();
            Weather weather = weatherList.get(0);

            RecommendStarResponseDto recommendStarResponseDto = new RecommendStarResponseDto(
                    location.getCityName(),
                    star.getStarGazing(),
                    weather.getTemperature(),
                    location.getImg()
            );

            recommendStarResponseDtos.add(recommendStarResponseDto);
        }
        List<String> currentTime = Timestamped.getCurrentTime();


        starHotDto starHotDto = new starHotDto(currentTime.get(3), recommendStarResponseDtos);


        return starHotDto;
    }

    public StarPhotoDto getStarPhoto() {
        try {
            List<String> currentTime = Timestamped.getCurrentTime();
            String month = currentTime.get(2);
            log.info("month = {}", month);

            Integer intMonth = Integer.valueOf(month);
            if (intMonth < 10) {
                if (month.length() >= 2) {
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
        catch(NullPointerException nullPointerException){
            throw new NullPointerException(ErrorCode.NotFoundBoard.getMessage());
        }
    }
}
