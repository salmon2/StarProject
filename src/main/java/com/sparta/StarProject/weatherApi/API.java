package com.sparta.StarProject.weatherApi;

import com.sparta.StarProject.domain.Location;
import com.sparta.StarProject.domain.Star;
import com.sparta.StarProject.domain.board.Board;
import com.sparta.StarProject.domain.board.Camping;
import com.sparta.StarProject.domain.board.UserMake;
import com.sparta.StarProject.dto.*;
import com.sparta.StarProject.repository.CampingRepository;
import com.sparta.StarProject.repository.LocationRepository;
import com.sparta.StarProject.repository.StarRepository;
import com.sparta.StarProject.weatherApi.accuweatherAPI.AccuWeatherApi;
import com.sparta.StarProject.weatherApi.accuweatherAPI.StarGazingCity;
import com.sparta.StarProject.weatherApi.dustApi.DustApi;
import com.sparta.StarProject.weatherApi.dustApi.DustCity;
import com.sparta.StarProject.weatherApi.moonRiseAPI.MoonAPI;
import com.sparta.StarProject.weatherApi.moonRiseAPI.MoonCity;
import com.sparta.StarProject.weatherApi.weatherAPI.WeatherApi;
import com.sparta.StarProject.weatherApi.weatherAPI.WeatherCity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class API {
    private final AccuWeatherApi accuWeatherApi;
    private final DustApi dustApi;
    private final MoonAPI moonAPI;
    private final WeatherApi weatherApi;

    private final CampingRepository campingRepository;
    private final LocationRepository locationRepository;
    private final StarRepository starRepository;


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

    @Transactional
    public void findInfoByAddress(Board board, String address) throws Exception {
        List<String> location = processAddress(address);

        WeatherCity weatherCity = WeatherCity.getWeatherCityByString(location.get(0));
        MoonCity moonCity = MoonCity.getMoonCityByString(location.get(0), location.get(1));
        DustCity dustCity = DustCity.getDustCityByString(location.get(1));
        StarGazingCity starGazingCity = StarGazingCity.getStarGazingCityByString(location.get(0));

        List<StarGazingDto> starGazing = accuWeatherApi.getStarGazing(starGazingCity);
        SunMoonDto moon = moonAPI.getMoon(moonCity);
        List<WeatherApiDto2> weather = weatherApi.getWeather(weatherCity);
        DustApiDto dust = dustApi.getDust(dustCity);

        Location newLocation = new Location(null, null, address, location.get(0), board);
        Location saveLocation = locationRepository.save(newLocation);



    }

}
