package com.sparta.StarProject.service;

import com.sparta.StarProject.domain.Location;
import com.sparta.StarProject.domain.Star;
import com.sparta.StarProject.dto.StarResponseDto;
import com.sparta.StarProject.repository.LocationRepository;
import com.sparta.StarProject.repository.StarRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StarService {

    private final StarRepository starRepository;
    private final LocationRepository locationRepository;


    public StarResponseDto readStarInfo(Long locationId) {
        Optional<Location> findLocation = locationRepository.findById(locationId);
        Location location = findLocation.get();

        Star findStar = location.getStar();

        StarResponseDto starResponseDto = new StarResponseDto(
                    findStar.getMoonrise(),
                    findStar.getMoonSet(),
                    findStar.getVisibility(),
                    findStar.getHumidity(),
                    findStar.getWeather(),
                    findStar.getTemperature()
        );

        return starResponseDto;
    }
}
