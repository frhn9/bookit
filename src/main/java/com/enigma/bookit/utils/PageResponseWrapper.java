package com.enigma.bookit.utils;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageResponseWrapper<T> {
    private List<T> data;
    private Integer code;
    private String status;

    public PageResponseWrapper(List<T> data, Integer code, String status) {
        this.data = data;
        this.code = code;
        this.status = status;
    }
}
