package com.sparta.StarProject.controller;

import com.sparta.StarProject.domain.Location;
import com.sparta.StarProject.dto.ResponseDto;
import com.sparta.StarProject.dto.StarResponseDto;
import com.sparta.StarProject.service.StarService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StarController {

    private final StarService starService;

    public StarController(StarService starService) {
        this.starService = starService;
    }

    @GetMapping("/star/info")
    public ResponseDto readStarInfo(@RequestParam("locationId") Long locationId){
        StarResponseDto starResponseDto = starService.readStarInfo(locationId);

        return new ResponseDto(200L, "성공", starResponseDto);
    }

}
