package com.enigma.bookit.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @NoArgsConstructor
@Table(name="trx_book")
public class Book {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name ="system-uuid", strategy="uuid")
    @Column(name=" book_id")
    private String id;
    @Column(name ="active_from",columnDefinition = "TIMESTAMP")
    private LocalDateTime activeFrom;
    @Column(name ="active_until",columnDefinition = "TIMESTAMP")
    private LocalDateTime activeUntil;

    @OneToOne
    @JoinColumn(name="payment_id")
    @JsonIgnoreProperties("payment")
    private Payment payment;

    public Book(String id, LocalDateTime activeFrom, LocalDateTime activeUntil, Payment payment) {
        this.id = id;
        this.activeFrom = activeFrom;
        this.activeUntil = activeUntil;
        this.payment = payment;
    }
}
