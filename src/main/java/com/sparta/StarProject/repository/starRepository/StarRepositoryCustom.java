package com.sparta.StarProject.repository.starRepository;


import com.sparta.StarProject.dto.StarWeatherResponseDto;

public interface StarRepositoryCustom {
    StarWeatherResponseDto findStarWeatherResponse(String cityName, String predictTime);
}
