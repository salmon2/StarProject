package com.sparta.StarProject.controller;


import com.sparta.StarProject.domain.Location;
import com.sparta.StarProject.dto.RecommendStarResponseDto;
import com.sparta.StarProject.dto.ResponseDto;
import com.sparta.StarProject.dto.StarInfoResponseDto;
import com.sparta.StarProject.dto.StarWeatherResponseDto;
import com.sparta.StarProject.repository.LocationRepository;
import com.sparta.StarProject.service.StarService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class StarController {
    private final StarService starService;
    private  final LocationRepository locationRepository;
//    private final API api;
//
//    @GetMapping("/star/info")
//    public ResponseDto readStarGuide(@RequestParam("locationId") Long locationId){
//        StarGuideResponseDto starGuideResponseDto = starService.readStarGuide(locationId);
//
//        return new ResponseDto(200L, "성공", starGuideResponseDto);
//    }
//
//    @GetMapping("/test")
//    public void test(@RequestParam String location) throws Exception {
//        System.out.println("========================================================================");
//        System.out.println("location = " + location);
//    }

//    @GetMapping("/star/info")
//    public ResponseDto getStarInfo(@RequestParam("location") String cityName){
//        StarInfoResponseDto starInfoResponseDto = starService.getStarInfo(cityName);
//
//        return new ResponseDto(200L, "성공", starInfoResponseDto);
//    }


    @GetMapping("/star/info")
    public ResponseDto getStarInfo(@RequestParam("location") String cityName, @RequestParam("time") String predictTime){
        StarWeatherResponseDto starWeatherResponseDto = starService.getWeatherInfo(cityName, predictTime);

        return new ResponseDto(200L, "성공", starWeatherResponseDto);
    }

//    @GetMapping("/star/hot")
//    public ResponseDto recommendStar(@PathVariable Long starGazing) {
//        RecommendStarResponseDto recommendStarResponseDto = starService.recommendStar(starGazing);
//
//        return  new ResponseDto(200L, "성공", recommendStarResponseDto);
//
//    }

}
