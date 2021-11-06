package com.sparta.StarProject.controller;


import com.sparta.StarProject.api.StellaList;
import com.sparta.StarProject.domain.StarInfo;
import com.sparta.StarProject.dto.*;
import com.sparta.StarProject.repository.StarInfoRepository;
import com.sparta.StarProject.service.StarService;
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
    private final StarInfoRepository starInfoRepository;

    @GetMapping("/star/photo/saveData")
    public ResponseDto saveStarPhoto(){
        starInfoRepository.deleteAll();
        for (StellaList value : StellaList.values()) {
            StarInfo starInfo = new StarInfo(value.getImg(), value.getName(), value.getComment(), value.getMonth().toString());
            starInfoRepository.save(starInfo);
        }
        return null;
    }


    @GetMapping("/star/photo")
    public ResponseDto getStarPhoto(){
        StarPhotoDto starPhotoDto = starService.getStarPhoto();

        return new ResponseDto(200L, "성공", starPhotoDto);
    }

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
        starHotDto starHotDto = starService.recommendStar();

        return  new ResponseDto(200L, "성공", starHotDto);

    }


}
