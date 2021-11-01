package com.sparta.StarProject.controller;


import com.sparta.StarProject.dto.ResponseDto;
import com.sparta.StarProject.dto.StarInfoResponseDto;
import com.sparta.StarProject.service.StarService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StarController {
    private final StarService starService;
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

    @GetMapping("/star/info")
    public ResponseDto getStarInfo(@RequestParam("location") String cityName){
        StarInfoResponseDto starInfoResponseDto = starService.getStarInfo(cityName);

        return new ResponseDto(200L, "성공", starInfoResponseDto);
    }
}
