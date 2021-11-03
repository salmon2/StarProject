package com.sparta.StarProject.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class RestApiException {
    private String code;
    private String msg;
    private HttpStatus data;
}
