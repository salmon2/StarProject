package com.sparta.StarProject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestApiExceptionHandler {

    @ExceptionHandler(value = {StarProjectException.class})
    public ResponseEntity<Object> handleApiRequestException(StarProjectException ex) {
        RestApiException restApiException = new RestApiException();
        restApiException.setCode("500");
        restApiException.setData(null);
//        restApiException.setMsg("알 수 없는 이유로 실패하였습니다.");
//        restApiException.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        restApiException.setMsg(ex.getErrorCode().getMessage());
        return new ResponseEntity<>(
                restApiException,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}