package com.sparta.StarProject.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum  ErrorCode {

    PHOTO_NOT_FOUND(INTERNAL_SERVER_ERROR, "알 수 없는 이유로 실패하였습니다."),

    USERNAME_DUPLICATE(NOT_IMPLEMENTED, "사용 불가능한 유저네임 입니다."),
    USER_NOT_FOUND(INTERNAL_SERVER_ERROR, "알 수 없는 이유로 실패하였습니다."),

    NICKNAME_DUPLICATE(NOT_IMPLEMENTED, "사용 불가능한 닉네임입니다."),

    PASSWORD_CHECK(NOT_IMPLEMENTED, "비밀번호가 일치 하지 않습니다."),
    LOGIN_TOKEN_EXPIRE(INTERNAL_SERVER_ERROR, "로그인이 만료되었습니다. 재로그인 하세요.");

    private final HttpStatus httpStatus;
    private final String message;
}