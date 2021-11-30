package com.sparta.StarProject.repository.weatherRepository;

import com.sparta.StarProject.domain.board.Board;
import com.sparta.StarProject.dto.DetailWeatherCityInfoDto;
import com.sparta.StarProject.dto.DetailWeatherList;

import java.util.List;

public interface WeatherRepositoryCustom {
    List<DetailWeatherList> findDetailWeatherListByBoardId(Long id);
    DetailWeatherCityInfoDto findDetailWeatherCityInfoByBoardId(Long id);
}
