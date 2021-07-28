package com.enigma.bookit.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor
public class Response<T> {

    private Integer code;
    private String status;
    private String message;
    private LocalDateTime timestamp;
    private T data;
<<<<<<< HEAD
}
=======



    }
>>>>>>> dinny

