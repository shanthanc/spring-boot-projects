package com.shanthan.ibo.domain;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.Map;

@Value
@Builder
public class ErrorResponse {

    LocalDateTime timeStamp;
    String message;
    Map<Object,String> errorFields;
}
