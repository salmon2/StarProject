package com.sparta.StarProject.controller;

import com.sparta.StarProject.domain.User;
import com.sparta.StarProject.dto.*;
import com.sparta.StarProject.exception.ErrorCode;
import com.sparta.StarProject.exception.StarProjectException;
import com.sparta.StarProject.security.UserDetailsImpl;
import com.sparta.StarProject.security.jwt.JwtTokenProvider;
import com.sparta.StarProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
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
        return new ResponseDto(200L, "성공", null);
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

    @GetMapping("/user/username/check")
    public Map<String, String> sameUsername(@RequestParam String username) {
        return userService.sameUsername(username);
    }

    @GetMapping("/user/nickname/check")
    public Map<String, String> sameNickname(@RequestParam String nickname) {
        return userService.sameNickname(nickname);
    }

    @GetMapping("/my/writeList")
    public ResponseDto getBoardList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
//        if(userDetails == null){
//            throw new StarProjectException(ErrorCode.LOGIN_TOKEN_EXPIRE);
//        }
        List<BoardDto> boardDto = userService.getBoardList(userDetails.getUser());
        return new ResponseDto(200L, "성공", boardDto);
    }
}