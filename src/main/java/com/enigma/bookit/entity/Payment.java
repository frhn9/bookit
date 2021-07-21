package com.enigma.bookit.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigInteger;
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
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "facility_id")
    @JsonIgnoreProperties("payment")
    private Facility facility;
    @Enumerated(EnumType.STRING)
    private PackageChosen packageChosen;
    private BigInteger amount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date bookingStart;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date bookingEnd;
    private boolean paymentStatus;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date dueTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date paymentDate;


}
