package com.sparta.StarProject.service;

import com.sparta.StarProject.domain.Location;
import com.sparta.StarProject.domain.Star;
import com.sparta.StarProject.domain.Weather;
import com.sparta.StarProject.dto.RecommendStarResponseDto;
import com.sparta.StarProject.dto.StarInfoResponseDto;
import com.sparta.StarProject.dto.StarWeatherResponseDto;
import com.sparta.StarProject.repository.LocationRepository;
import com.sparta.StarProject.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StarService {

    private final LocationRepository locationRepository;
    private final WeatherRepository weatherRepository;
//
//    public StarGuideResponseDto readStarGuide(Long locationId) {
//        Optional<Location> findLocation = locationRepository.findById(locationId);
//        Location location = findLocation.get();
//
//        Star findStar = location.getStar();
//
//        StarGuideResponseDto starGuideResponseDto = new StarGuideResponseDto(
//                    findStar.getMoonrise(),
//                    findStar.getMoonSet(),
//                    findStar.getHumidity(),
//                    findStar.getWeather(),
//                    findStar.getTemperature()
//        );
//
//        return null;
//    }

//    public StarInfoResponseDto getStarInfo(String cityName){
//        List<Location> findLocation = locationRepository.findAllByCityName(cityName);
//        Location location = findLocation.get(0);
//
//        Star findStar = location.getStar();
//
//        StarInfoResponseDto starInfoResponseDto = new StarInfoResponseDto(
//                findStar.getMoonrise(),
//                findStar.getMoonSet(),
//                findStar.getStarGazing()
//        );
//        return starInfoResponseDto;
//
//    }

//    public StarWeatherResponseDto getWeatherInfo(String cityName, String predictTime) {
//
//        Optional<Location> location = locationRepository.findAllByCityName(cityName);
//
//        List<Weather> getTime = weatherRepository.findAllByPredictTime(predictTime);
//        Weather weather = getTime.get(0);
//
////        Weather findWeather = weather.getWeather();
//
//        StarWeatherResponseDto starWeatherResponseDto = new StarWeatherResponseDto(
//                weather.getLocation(),
//                weather.getRainPercent(),
//                weather.getHumidity(),
//                weather.getWeather(),
//                weather.getTemperature(),
//                weather.getMaxTemperature(),
//                weather.getMinTemperature()
//        );
//        return starWeatherResponseDto;
//    }

    public StarWeatherResponseDto getWeatherInfo(String cityName, String predictTime) {

        Location location = locationRepository.findAllByCityName(cityName);

        List<Weather> getTime = weatherRepository.findAllByPredictTime(predictTime);
        Weather weather = getTime.get(0);

//        Weather findWeather = weather.getWeather();

        StarWeatherResponseDto starWeatherResponseDto = new StarWeatherResponseDto(
                weather.getLocation(),
                weather.getRainPercent(),
                weather.getHumidity(),
                weather.getWeather(),
                weather.getTemperature(),
                weather.getMaxTemperature(),
                weather.getMinTemperature()
        );
        return starWeatherResponseDto;
    }

//    public RecommendStarResponseDto recommendStar(Long starGazing) {
//
//    }
}
