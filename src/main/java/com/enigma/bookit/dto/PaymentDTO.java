package com.enigma.bookit.dto;

import com.enigma.bookit.entity.Facility;
import com.enigma.bookit.entity.PackageChosen;
import com.enigma.bookit.entity.user.Customer;
import com.enigma.bookit.entity.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    private String id;
    private Customer customer;
    private Facility facility;
    private PackageChosen packageChosen;
    private BigDecimal amount;
    private LocalDateTime bookingStart;
    private LocalDateTime bookingEnd;
    private boolean paymentStatus;
    private LocalDateTime dueTime;
    private LocalDateTime paymentDate;
}
