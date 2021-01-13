package com.shanthan.ibo.domain;

import lombok.Builder;
import lombok.Value;

import java.io.Serializable;

@Value
@Builder
public class Address implements Serializable {
    String addressLine1;
    String addressLine2;
    String city;
    String state;
    String zipCode;

}
