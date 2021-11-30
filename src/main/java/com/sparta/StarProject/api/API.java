package com.sparta.StarProject.api;

import com.sparta.StarProject.api.locationAPI.AddressToGps;
import com.sparta.StarProject.domain.Location;
import com.sparta.StarProject.domain.Star;
import com.sparta.StarProject.domain.Weather;
import com.sparta.StarProject.dto.*;
import com.sparta.StarProject.repository.LocationRepository;
import com.sparta.StarProject.repository.starRepository.StarRepository;
import com.sparta.StarProject.repository.weatherRepository.WeatherRepository;
import com.sparta.StarProject.api.accuweatherAPI.AccuWeatherApi;
import com.sparta.StarProject.api.accuweatherAPI.StarGazingCity;
import com.sparta.StarProject.api.dustApi.DustApi;
import com.sparta.StarProject.api.dustApi.DustCity;
import com.sparta.StarProject.api.moonRiseAPI.MoonAPI;
import com.sparta.StarProject.api.moonRiseAPI.MoonCity;
import com.sparta.StarProject.api.weatherAPI.WeatherApi;
import com.sparta.StarProject.api.weatherAPI.WeatherCity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class API {
    private final AccuWeatherApi accuWeatherApi;
    private final DustApi dustApi;
    private final MoonAPI moonAPI;
    private final WeatherApi weatherApi;
    private final AddressToGps addressToGps;

    private final LocationRepository locationRepository;
    private final StarRepository starRepository;
    private final WeatherRepository weatherRepository;


    //주소를 city로 찾기
    public List<String> processAddress(String address){
        List<String> result = new ArrayList<>();

        for (StarGazingCity value : StarGazingCity.values()) {
            if(address.contains(value.getKorName())){
                result.add(value.getKorName());
                result.add(value.getState());
                return result;
            }
        }
        return result;
    }

    //외부 api 쏘기
    public LocationStarMoonDustDto findInfoByAddress(String address, int count) throws Exception {
        List<String> location = processAddress(address);    //경상북도 구미시, 구미
                                                            //서울특별시 ~~, 서울

        WeatherCity weatherCity = WeatherCity.getWeatherCityByString(location.get(0));
        MoonCity moonCity = MoonCity.getMoonCityByString(location.get(0), location.get(1));
        DustCity dustCity = DustCity.getDustCityByString(location.get(1));
        StarGazingCity starGazingCity = StarGazingCity.getStarGazingCityByString(location.get(0));


        log.info("weatherCity = {}",weatherCity);
        log.info("moonCity = {}",moonCity);
        log.info("dustCity = {}",dustCity);
        log.info("starGazingCity = {}",starGazingCity);

        List<StarGazingDto> starGazing = null;
        SunMoonDto moon = null;
        List<WeatherApiDto2> weather = null;
        DustApiDto dust = null;
        GeographicDto geographicDto = null;

        do{
            starGazing = accuWeatherApi.getStarGazing(starGazingCity, count);
        }while(starGazing == null);

        do{
            moon = moonAPI.getMoon(moonCity);
        }while(moon == null);

        do{
            weather = weatherApi.getWeather(weatherCity);
        }while(weather == null);

        do{
            dust = dustApi.getDust(dustCity);
        }while(dust == null);

        do{
            geographicDto = addressToGps.getAddress(address);
        }while(geographicDto ==null);


        LocationStarMoonDustDto result = new LocationStarMoonDustDto(
                starGazing,
                moon,
                weather,
                dust,
                address,
                geographicDto
        );


        return result;
    }

    //외부 api 쏜거 저장하기
    @Transactional
    public Location saveStarLocationWeather(LocationStarMoonDustDto result ) {
        List<String> location = processAddress(result.getAddress());
        Location newLocation = new Location(location.get(0));

        Location saveLocation = locationRepository.save(newLocation);

        Star newStar =
                new Star(
                    result.getMoon().getMoonrise(),
                    result.getMoon().getMoonSet(),
                    Long.valueOf(result.getStarGazing().get(0).getValue().longValue()),
                    saveLocation
                );
        starRepository.save(newStar);

        for (WeatherApiDto2 weatherApiDto2 : result.getWeather()) {
            Weather newWeather = new Weather(
                            weatherApiDto2.getHumidity(),
                            weatherApiDto2.getWeather(),
                            weatherApiDto2.getTemperature(),
                            weatherApiDto2.getMaxTemperature(),
                            weatherApiDto2.getMinTemperature(),
                            weatherApiDto2.getPrecipitationProbability(),
                            weatherApiDto2.getFcstTime(),
                            result.getDust().getPm10Value(),
                            weatherApiDto2.getBaseDate(),
                            saveLocation);

            weatherRepository.save(newWeather);
        }

        return saveLocation;
    }



}
