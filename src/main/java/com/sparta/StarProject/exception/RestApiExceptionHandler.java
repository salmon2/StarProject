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
            ServerError(ex, restApiException, 501L);
        }
        else if (errorCode.equals(ErrorCode.USERNAME_DUPLICATE)){
            ServerError(ex, restApiException, 501L);
        }
        else {
            //공통 에러 제어
            ServerError(ex, restApiException, 500L);
        }

        return new ResponseEntity<>(
                restApiException,
                HttpStatus.OK
        );
    }

    @ExceptionHandler(value = {NullPointerException.class})
    public ResponseEntity<Object> NullPointExceptionHandler(StarProjectException ex) {
        return null;
    }

    private void ServerError(StarProjectException ex, RestApiException restApiException, long l) {
        restApiException.setCode(l);
        restApiException.setData(null);
        restApiException.setMsg(ex.getErrorCode().getMessage());
    }


}