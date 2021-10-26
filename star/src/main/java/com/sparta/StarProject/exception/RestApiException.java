package com.sparta.StarProject.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class RestApiException {
    private String msg;
    private String errorMessage;
    private HttpStatus httpStatus;
}
