package com.sparta.StarProject.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum  ErrorCode {

    PHOTO_NOT_FOUND(INTERNAL_SERVER_ERROR, "알 수 없는 이유로 실패하였습니다."),

    BOARD_NOT_FOUND(INTERNAL_SERVER_ERROR,"게시물을 찾을수 없습니다."),

    USERNAME_AVAILABLE(OK, "사용 가능한 유저네임입니다."),
    USERNAME_NOT_FOUND(INTERNAL_SERVER_ERROR, "아이디를 입력해주세요."),
    USERNAME_DUPLICATE(NOT_IMPLEMENTED, "사용 불가능한 유저네임 입니다."),
    USER_NOT_FOUND(INTERNAL_SERVER_ERROR, "알 수 없는 이유로 실패하였습니다."),

    NICKNAME_AVAILABLE(OK, "사용 가능한 닉네임입니다."),
    NICKNAME_NOT_FOUND(INTERNAL_SERVER_ERROR, "닉네임을 입력해주세요."),
    NICKNAME_DUPLICATE(NOT_IMPLEMENTED, "사용 불가능한 닉네임입니다."),

    PASSWORD_FOT_FOUND(INTERNAL_SERVER_ERROR,"비밀번호를 입력해주세요."),
    PASSWORD_CHECK(NOT_IMPLEMENTED, "비밀번호가 일치 하지 않습니다."),
    LOGIN_TOKEN_EXPIRE(INTERNAL_SERVER_ERROR, "login fail");


    private final HttpStatus httpStatus;
    private final String message;
}