package com.enigma.bookit.dto;

import com.enigma.bookit.entity.PackageChosen;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
public class PaymentSearchDTO {
    private String customerName;
    private String facilityName;
    private PackageChosen packageChosen;
    private BigDecimal amountStart;
    private BigDecimal amountStop;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp bookingStartFrom;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp bookingStartUntil;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp bookingEndFrom;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp bookingEndUntil;
    private Boolean paymentStatus;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp dueTimeStart;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp dueTimeEnd;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp paymentDateFrom;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp paymentDateUntil;

    public PaymentSearchDTO(String customerName, String facilityName, PackageChosen packageChosen, BigDecimal amountStart, BigDecimal amountStop, Timestamp bookingStartFrom, Timestamp bookingStartUntil, Timestamp bookingEndFrom, Timestamp bookingEndUntil, Boolean paymentStatus, Timestamp dueTimeStart, Timestamp dueTimeEnd, Timestamp paymentDateFrom, Timestamp paymentDateUntil) {
        this.customerName = customerName;
        this.facilityName = facilityName;
        this.packageChosen = packageChosen;
        this.amountStart = amountStart;
        this.amountStop = amountStop;
        this.bookingStartFrom = bookingStartFrom;
        this.bookingStartUntil = bookingStartUntil;
        this.bookingEndFrom = bookingEndFrom;
        this.bookingEndUntil = bookingEndUntil;
        this.paymentStatus = paymentStatus;
        this.dueTimeStart = dueTimeStart;
        this.dueTimeEnd = dueTimeEnd;
        this.paymentDateFrom = paymentDateFrom;
        this.paymentDateUntil = paymentDateUntil;
    }
}
