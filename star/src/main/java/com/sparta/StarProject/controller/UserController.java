package com.sparta.StarProject.controller;

import com.sparta.StarProject.domain.User;
import com.sparta.StarProject.dto.SignUpRequestDto;
import com.sparta.StarProject.dto.UserRequestDto;
import com.sparta.StarProject.exception.StarProjectException;
import com.sparta.StarProject.security.jwt.JwtTokenProvider;
import com.sparta.StarProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserController(UserService userService, JwtTokenProvider jwtTokenProvider){
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    //가입요청
    @PostMapping("/user/signup")
    public Map<String, String> registerUser(@RequestBody SignUpRequestDto requestDto)
            throws StarProjectException{
        User user = userService.registerUser(requestDto);
        Map<String, String> msg = new HashMap<>();
            msg.put("msg","성공");
            return msg;
    }

    //로그인
    @PostMapping("/user/login")
    public List<Map<String, Object>> login(@RequestBody UserRequestDto requestDto) throws StarProjectException{
        User user = userService.login(requestDto);

        java.util.List<Map<String,Object>> addObject = new ArrayList<>();
        Map<String, Object> msg = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
            data.put("token", jwtTokenProvider.createToken(user.getUsername(), user.getUsername(), user.getNickname()));
            data.put("username", user.getUsername());
            data.put("nickname", user.getNickname());
            msg.put("msg", "성공");
            addObject.add(msg);
            addObject.add(data);

            return addObject;
    }


}

