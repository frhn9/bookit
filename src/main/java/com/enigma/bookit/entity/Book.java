package com.enigma.bookit.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Getter @Setter @NoArgsConstructor
@Table(name="trx_book")
public class Book {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name ="system-uuid", strategy="uuid")
    @Column(name="book_id")
    private String id;
    @Column(name ="active_from")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Timestamp activeFrom;
    @Column(name ="active_until")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Timestamp activeUntil;

    @OneToOne
    @JoinColumn(name="payment_id")
    @JsonIgnoreProperties("payment")
    private Payment payment;


    public Book(String id, Timestamp activeFrom, Timestamp activeUntil, Payment payment) {
        this.id = id;
        this.activeFrom = activeFrom;
        this.activeUntil = activeUntil;
        this.payment = payment;
    }
}
