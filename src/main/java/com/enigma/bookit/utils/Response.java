package com.enigma.bookit.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response<T> {
    private Integer code;
    private String status;
    private T data;
}
