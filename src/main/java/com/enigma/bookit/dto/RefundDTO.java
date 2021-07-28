package com.enigma.bookit.dto;

import com.enigma.bookit.entity.Book;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter @Getter @NoArgsConstructor
public class RefundDTO {
    private String id;
    private Book book;
    private LocalDateTime requestRefundTime;
    private LocalDateTime refundTime;
    private Boolean status;
    private BigDecimal refundAmount;
}
