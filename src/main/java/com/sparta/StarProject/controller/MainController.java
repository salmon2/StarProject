package com.sparta.StarProject.controller;

import com.sparta.StarProject.dto.MainDto;
import com.sparta.StarProject.dto.ResponseDto;
import com.sparta.StarProject.security.UserDetailsImpl;
import com.sparta.StarProject.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MainController {
    private final MainService mainService;

    @GetMapping("/main/boardList")
    public ResponseDto mainList(@AuthenticationPrincipal UserDetailsImpl userDetails){
        List<MainDto> mainDto = mainService.mainList(userDetails);

        return new ResponseDto(200L, "성공", mainDto);
    }
}
