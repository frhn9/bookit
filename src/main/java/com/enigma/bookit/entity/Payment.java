package com.enigma.bookit.entity;


import com.enigma.bookit.entity.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "trx_payment")
@Getter @Setter @NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "payment_id")
    private String id;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnoreProperties("payment")
    private User customer;
    @ManyToOne
    @JoinColumn(name = "facility_id")
    @JsonIgnoreProperties("payment")
    private Facility facility;
    @Enumerated(EnumType.STRING)
    private PackageChosen packageChosen;
    private BigDecimal paidAmount;
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime bookingStart;
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime bookingEnd;
    private String paymentStatus;
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime dueTime;
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime paymentDate;


}
