package com.sparta.StarProject.controller;

import com.sparta.StarProject.domain.User;
import com.sparta.StarProject.dto.ResponseDto;
import com.sparta.StarProject.dto.SignUpRequestDto;
import com.sparta.StarProject.dto.UserRequestDto;
import com.sparta.StarProject.exception.ErrorCode;
import com.sparta.StarProject.exception.StarProjectException;
import com.sparta.StarProject.security.UserDetailsImpl;
import com.sparta.StarProject.security.jwt.JwtTokenProvider;
import com.sparta.StarProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserController(UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    //가입요청
    @PostMapping("/user/signup")
    public ResponseDto registerUser(@RequestBody SignUpRequestDto requestDto)
            throws StarProjectException {
        User user = userService.registerUser(requestDto);
        return new ResponseDto(200L,"성공",null);
    }

    //로그인
    @PostMapping("/user/login")
    public ResponseDto login(@RequestBody UserRequestDto requestDto) throws StarProjectException {
        User user = userService.login(requestDto);

        Map<String, Object> data = new HashMap<>();
        data.put("token", jwtTokenProvider.createToken(user.getUsername(), user.getUsername(), user.getNickname()));
        data.put("username", user.getUsername());
        data.put("nickname", user.getNickname());


        return new ResponseDto(200L, "성공", data);
    }

    @GetMapping("/user/login/check")
    public ResponseDto loginCheck(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        if (userDetails == null) {
            throw new StarProjectException(ErrorCode.LOGIN_TOKEN_EXPIRE);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("username", userDetails.getUser().getUsername());
        data.put("nickname", userDetails.getUser().getNickname());

        return new ResponseDto(200L, "성공", data);
    }
}