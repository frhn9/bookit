package com.enigma.bookit.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Getter @Setter @NoArgsConstructor
@Table(name="trx_book")
public class Book {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name ="system-uuid", strategy="uuid")
    @Column(name="book_id")
    private String id;
    private Date active_from;
    private Date active_until;

    @OneToOne
    @JoinColumn(name="payment_id")
    @JsonIgnoreProperties("payment")
    private Payment payment;

    public Book(String id, Date active_from, Date active_until, Payment payment) {
        this.id = id;
        this.active_from = active_from;
        this.active_until = active_until;
        this.payment = payment;
    }
}
