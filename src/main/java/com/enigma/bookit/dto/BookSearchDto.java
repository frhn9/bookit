package com.enigma.bookit.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor
public class BookSearchDto {
    private LocalDateTime activeFrom;
    private LocalDateTime activeUntil;

    public BookSearchDto(LocalDateTime activeFrom, LocalDateTime activeUntil) {
        this.activeFrom = activeFrom;
        this.activeUntil = activeUntil;
    }
}
