package com.shanthan.ibo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class IboException extends Exception {
    private final HttpStatus httpStatus;

    public IboException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
