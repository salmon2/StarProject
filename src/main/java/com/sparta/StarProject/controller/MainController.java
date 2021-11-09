package com.sparta.StarProject.controller;

import com.sparta.StarProject.dto.MainDto;
import com.sparta.StarProject.dto.ResponseDto;
import com.sparta.StarProject.service.MainService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainController {

    private final MainService mainService;

    public MainController(MainService mainService) {
        this.mainService = mainService;
    }

    @GetMapping("/main/boardList")
    public ResponseDto mainList(){
        List<MainDto> mainDto = mainService.mainList();

        return new ResponseDto(200L, "성공", mainDto);
    }
}
