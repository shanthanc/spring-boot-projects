package com.shanthan.ibo.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class StatusResponse {
    Long iboNumber;
    String status;
}
