package com.sparta.StarProject.service;

import com.sparta.StarProject.domain.Location;
import com.sparta.StarProject.domain.Star;
import com.sparta.StarProject.dto.StarInfoResponseDto;
import com.sparta.StarProject.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StarService {

    private final LocationRepository locationRepository;
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

    public StarInfoResponseDto getStarInfo(String cityName){
        List<Location> findLocation = locationRepository.findAllByCityName(cityName);
        Location location = findLocation.get(0);

        Star findStar = location.getStar();

        StarInfoResponseDto starInfoResponseDto = new StarInfoResponseDto(
                findStar.getMoonrise(),
                findStar.getMoonSet(),
                findStar.getStarGazing()
        );
        return starInfoResponseDto;

    }




}
