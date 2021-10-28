package com.sparta.StarProject.controller;

import com.sparta.StarProject.dto.ResponseDto;
import com.sparta.StarProject.dto.StarGuideResponseDto;
import com.sparta.StarProject.service.StarService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StarController {

    private final StarService starService;

    public StarController(StarService starService) {
        this.starService = starService;
    }

    @GetMapping("/star/info")
    public ResponseDto readStarGuide(@RequestParam("locationId") Long locationId){
        StarGuideResponseDto starGuideResponseDto = starService.readStarGuide(locationId);

        return new ResponseDto(200L, "성공", starGuideResponseDto);
    }

}