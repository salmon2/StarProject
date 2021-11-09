package com.sparta.StarProject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestApiExceptionHandler {

    @ExceptionHandler(value = {StarProjectException.class})
    public ResponseEntity<Object> handleApiRequestException(StarProjectException ex) {

        ErrorCode errorCode = ex.getErrorCode();
        RestApiException restApiException = new RestApiException();


        if(errorCode.equals(ErrorCode.NICKNAME_DUPLICATE)){
            restApiException.setCode(501L);
            restApiException.setData(null);
            restApiException.setMsg(ex.getErrorCode().getMessage());
        }
        else if (errorCode.equals(ErrorCode.USERNAME_DUPLICATE)){
            restApiException.setCode(501L);
            restApiException.setData(null);
            restApiException.setMsg(ex.getErrorCode().getMessage());
        }
       else {
            //공통 에러 제어
            restApiException.setCode(500L);
            restApiException.setData(null);
            restApiException.setMsg(ex.getErrorCode().getMessage());
        }
        return new ResponseEntity<>(
                restApiException,
                HttpStatus.OK
        );
    }
}