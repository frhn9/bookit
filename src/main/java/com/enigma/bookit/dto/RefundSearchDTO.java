package com.enigma.bookit.dto;

import com.enigma.bookit.entity.Book;
import com.enigma.bookit.service.BookService;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter @Setter @NoArgsConstructor
public class RefundSearchDTO {


    private Book book;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Timestamp requestRefundTimeStart;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Timestamp requestRefundTimeStop;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Timestamp refundTimeStart;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Timestamp refundTimeStop;
    private Boolean status;
    private BigDecimal amountMore;
    private BigDecimal amountLess;
    private String customerName;
    private String facilityName;

    public RefundSearchDTO(Book book, Timestamp requestRefundTimeStart, Timestamp requestRefundTimeStop, Timestamp refundTimeStart, Timestamp refundTimeStop, Boolean status, BigDecimal amountMore, BigDecimal amountLess, String customerName, String facilityName) {
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
