package com.sparta.StarProject.controller;

import com.sparta.StarProject.dto.ResponseDto;
import com.sparta.StarProject.dto.StarGuideResponseDto;
import com.sparta.StarProject.service.StarService;
import com.sparta.StarProject.weatherApi.API;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StarController {
    private final StarService starService;
    private final API api;

    @GetMapping("/star/info")
    public ResponseDto readStarGuide(@RequestParam("locationId") Long locationId){
        StarGuideResponseDto starGuideResponseDto = starService.readStarGuide(locationId);

        return new ResponseDto(200L, "성공", starGuideResponseDto);
    }

    @GetMapping("/test")
    public void test(@RequestParam String location) throws Exception {
        System.out.println("========================================================================");
        System.out.println("location = " + location);
    }



}
