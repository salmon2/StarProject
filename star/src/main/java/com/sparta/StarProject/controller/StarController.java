package com.sparta.StarProject.controller;

import com.sparta.StarProject.dto.PhotoRequestDto;
import com.sparta.StarProject.service.PhotoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class StarController {

    private final PhotoService photoService;

    public StarController(PhotoService photoService){
        this.photoService = photoService;
    }

    @GetMapping("/star/photo")
    public Map<String, Object> getStarInfo(@RequestParam Long id){
        return photoService.getStarInfo(id);
    }

}
