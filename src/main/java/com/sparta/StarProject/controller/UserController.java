package com.sparta.StarProject.controller;

import com.sparta.StarProject.domain.User;
import com.sparta.StarProject.dto.ResponseDto;
import com.sparta.StarProject.dto.SignUpRequestDto;
import com.sparta.StarProject.dto.UserRequestDto;
import com.sparta.StarProject.dto.UserUpdateDto;
import com.sparta.StarProject.dto.*;
import com.sparta.StarProject.exception.ErrorCode;
import com.sparta.StarProject.exception.StarProjectException;
import com.sparta.StarProject.security.UserDetailsImpl;
import com.sparta.StarProject.security.jwt.JwtTokenProvider;
import com.sparta.StarProject.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
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
    public ResponseDto loginCheck(@AuthenticationPrincipal UserDetailsImpl userDetails) throws StarProjectException {

        if (userDetails.getUser() == null) {
            throw new StarProjectException(ErrorCode.LOGIN_TOKEN_EXPIRE);
        }

        return new ResponseDto(200L, "login success", null);
    }

    @GetMapping("/user/username/check")
    public Map<String, String> sameUsername(@RequestParam String username) throws StarProjectException {
        return userService.sameUsername(username);
    }

    @GetMapping("/user/nickname/check")
    public Map<String, String> sameNickname(@RequestParam String nickname) throws StarProjectException {
        return userService.sameNickname(nickname);
    }


    @GetMapping("/my/leave")
    public ResponseDto myLeave(@AuthenticationPrincipal UserDetailsImpl userDetails){
        userService.myLeave(userDetails.getUser());

        return new ResponseDto(200L, "성공", null );
    }

    @PutMapping("/my/update")
    public ResponseDto myUpdate(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                @RequestBody UserUpdateDto userUpdateDto) throws StarProjectException {
        userService.myUpdate(userDetails.getUser(), userUpdateDto);
        return new ResponseDto(200L, "성공", null);
    }

    @GetMapping("/my/writeList")
    public ResponseDto getBoardList(@AuthenticationPrincipal UserDetailsImpl userDetails) throws StarProjectException {
        if(userDetails == null){
            throw new StarProjectException(ErrorCode.LOGIN_TOKEN_EXPIRE);
        }
        List<MyBoardDto> boardDto = userService.getBoardList(userDetails.getUser());
        return new ResponseDto(200L, "성공", boardDto);

    }
}