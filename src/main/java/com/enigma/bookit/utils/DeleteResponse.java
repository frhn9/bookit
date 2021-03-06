package com.enigma.bookit.utils;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DeleteResponse {
    private Integer code;
    private String status;
    private String message;
    private LocalDateTime timestamp;
}
