package com.enigma.bookit.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigInteger;
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
    private Date requestRefundTime;
    private Date refundDate;
    private Boolean status;
    private BigInteger refundAmount;

    public Refund(String id, Book book) {
        this.id = id;
        this.book = book;
    }
}
