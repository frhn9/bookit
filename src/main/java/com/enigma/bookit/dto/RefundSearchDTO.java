package com.enigma.bookit.dto;

import com.enigma.bookit.entity.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor
public class RefundSearchDTO {


    private Book book;
    private LocalDateTime requestRefundTimeStart;
    private LocalDateTime requestRefundTimeStop;
    private LocalDateTime refundTimeStart;
    private LocalDateTime refundTimeStop;
    private Boolean status;
    private BigDecimal amountMore;
    private BigDecimal amountLess;
    private String customerName;
    private String facilityName;

    public RefundSearchDTO(Book book, LocalDateTime requestRefundTimeStart, LocalDateTime requestRefundTimeStop, LocalDateTime refundTimeStart, LocalDateTime refundTimeStop, Boolean status, BigDecimal amountMore, BigDecimal amountLess, String customerName, String facilityName) {
        this.book = book;
        this.requestRefundTimeStart = requestRefundTimeStart;
        this.requestRefundTimeStop = requestRefundTimeStop;
        this.refundTimeStart = refundTimeStart;
        this.refundTimeStop = refundTimeStop;
        this.status = status;
        this.amountMore = amountMore;
        this.amountLess = amountLess;
        this.customerName = customerName;
        this.facilityName = facilityName;
    }
}
