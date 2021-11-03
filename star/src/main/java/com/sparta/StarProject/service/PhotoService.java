package com.sparta.StarProject.service;

import com.sparta.StarProject.domain.StarInfo;
import com.sparta.StarProject.repository.StarInfoRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PhotoService {

    private final StarInfoRepository starInfoRepository;

    public PhotoService(StarInfoRepository starInfoRepository){
        this.starInfoRepository = starInfoRepository;
    }

    public Map<String, Object> getStarInfo(Long id){
        StarInfo starInfo = starInfoRepository.getById(id);
        Map<String, Object> result = new HashMap<>();
        result.put("StarImg", starInfo.getStarImg());
        result.put("starName", starInfo.getStarName());
        result.put("Comment",starInfo.getComment());
        result.put("result", "success");

        return result;
    }
}
