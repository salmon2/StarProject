package com.sparta.StarProject.exception;

import lombok.Getter;

@Getter
public class StarProjectException extends RuntimeException {

    private final ErrorCode errorCode;

    public StarProjectException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
