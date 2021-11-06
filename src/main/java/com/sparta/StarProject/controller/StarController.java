package com.sparta.StarProject.controller;


import com.sparta.StarProject.dto.RecommendStarResponseDto;
import com.sparta.StarProject.dto.ResponseDto;
import com.sparta.StarProject.service.StarService;
import com.sparta.StarProject.dto.StarInfoResponseDto;
import com.sparta.StarProject.dto.StarWeatherResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StarController {
    private final StarService starService;


    @GetMapping("/star/info")
    public ResponseDto getStarInfo(@RequestParam double latitude, @RequestParam double longitude) throws Exception {
        StarInfoResponseDto starInfoResponseDto = starService.getStarInfo(latitude, longitude);

        return new ResponseDto(200L, "성공", starInfoResponseDto);
    }


    @GetMapping("/star/info/time")
    public ResponseDto getWeatherByTime(@RequestParam Double latitude, @RequestParam Double longitude, @RequestParam("time") String predictTime) throws Exception {
        StarWeatherResponseDto starWeatherResponseDto = starService.getWeatherInfo(latitude, longitude, predictTime);

        return new ResponseDto(200L, "성공", starWeatherResponseDto);
    }


    @GetMapping("/star/hot")
    public ResponseDto recommendStar() {
        List<RecommendStarResponseDto> recommendStarResponseDtos = starService.recommendStar();

        return  new ResponseDto(200L, "성공", recommendStarResponseDtos);

    }


}
