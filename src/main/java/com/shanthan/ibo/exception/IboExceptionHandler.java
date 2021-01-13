package com.shanthan.ibo.exception;

import com.shanthan.ibo.domain.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;


@Slf4j
@RestControllerAdvice
public class IboExceptionHandler {

    @ExceptionHandler(IboException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleIboException(IboException iboException) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(iboException.getMessage())
                .timeStamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(iboException.getHttpStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }

}
