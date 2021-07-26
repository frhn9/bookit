package com.enigma.bookit.entity;

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
@Table(name = "mst_refund")
@Getter @Setter @NoArgsConstructor
public class Refund {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "refund_id")
    private String id;
    @ManyToOne
    @JoinColumn(name = "book_id")
    @JsonIgnoreProperties("refund")
    private Book book;
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime requestRefundTime;
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime refundTime;
    private Boolean status;
    private BigDecimal refundAmount;
}
