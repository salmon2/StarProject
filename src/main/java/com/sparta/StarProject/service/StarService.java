package com.sparta.StarProject.service;

import com.sparta.StarProject.domain.Location;
import com.sparta.StarProject.domain.Star;
import com.sparta.StarProject.dto.StarResponseDto;
import com.sparta.StarProject.repository.StarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StarService {

    private final StarRepository starRepository;

    @Autowired
    public StarService(StarRepository starRepository) {
        this.starRepository = starRepository;
    }


    public List<StarResponseDto> readStarInfo(Location location) {
        List<Star> stars = starRepository.findAllByLocation(location);
        List<StarResponseDto> starResponseDtos = new ArrayList<>();

        for(Star star : stars){
            StarResponseDto starResponseDto = new StarResponseDto(
                    star.getId(),
                    star.getMoonrise(),
                    star.getMoonSet(),
                    star.getVisibility(),
                    star.getHumidity(),
                    star.getWeather(),
                    star.getTemperature(),
                    star.getStarGazing(),
                    star.getLocation()
            );
        }
        return starResponseDtos;
    }
}
