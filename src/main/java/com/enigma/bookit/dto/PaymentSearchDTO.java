package com.enigma.bookit.dto;

import com.enigma.bookit.entity.PackageChosen;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
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
    private String paymentStatus;
    private LocalDateTime dueTimeStart;
    private LocalDateTime dueTimeEnd;
    private LocalDateTime paymentDateFrom;
    private LocalDateTime paymentDateUntil;

}
