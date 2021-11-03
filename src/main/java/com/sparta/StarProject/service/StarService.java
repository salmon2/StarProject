package com.sparta.StarProject.service;

import com.sparta.StarProject.domain.Location;
import com.sparta.StarProject.domain.Star;
import com.sparta.StarProject.dto.StarGuideResponseDto;
import com.sparta.StarProject.repository.LocationRepository;
import com.sparta.StarProject.repository.StarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StarService {

    private final LocationRepository locationRepository;

    public StarGuideResponseDto readStarGuide(Long locationId) {
        Optional<Location> findLocation = locationRepository.findById(locationId);
        Location location = findLocation.get();

        Star findStar = location.getStar();
//
//        StarGuideResponseDto starGuideResponseDto = new StarGuideResponseDto(
//                    findStar.getMoonrise(),
//                    findStar.getMoonSet(),
//                    findStar.getHumidity(),
//                    findStar.getWeather(),
//                    findStar.getTemperature()
//        );

        return null;
    }





}
