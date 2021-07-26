package com.enigma.bookit.dto;

import com.enigma.bookit.entity.PackageChosen;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class PaymentSearchDTO {
    private String customerName;
    private String facilityName;
    private PackageChosen packageChosen;
    private BigDecimal amountStart;
    private BigDecimal amountStop;
    private LocalDateTime bookingStartFrom;
    private LocalDateTime bookingStartUntil;
    private LocalDateTime bookingEndFrom;
    private LocalDateTime bookingEndUntil;
    private Boolean paymentStatus;
    private LocalDateTime dueTimeStart;
    private LocalDateTime dueTimeEnd;
    private LocalDateTime paymentDateFrom;
    private LocalDateTime paymentDateUntil;


    public PaymentSearchDTO(String customerName, String facilityName, PackageChosen packageChosen, BigDecimal amountStart, BigDecimal amountStop, LocalDateTime bookingStartFrom, LocalDateTime bookingStartUntil, LocalDateTime bookingEndFrom, LocalDateTime bookingEndUntil, Boolean paymentStatus, LocalDateTime dueTimeStart, LocalDateTime dueTimeEnd, LocalDateTime paymentDateFrom, LocalDateTime paymentDateUntil) {
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
