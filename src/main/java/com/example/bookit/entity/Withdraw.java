package com.example.bookit.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
@Table(name="mst_withdraw")
public class Withdraw {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name ="system-uuid", strategy="uuid")
    @Column(name="withdraw_id")
    private String id;
    @Column(name="withdraw_amount")
    private BigInteger withdrawAmount;
    @Column(name="withdraw_time")
    private Date withdrawTime;
    @Column(name="withdraw_method")
    private String withdrawMethod;
    @Column(name="withdraw_target")
    private String withdrawTarget;

    @OneToMany
    @JoinColumn(name="facility_id")
    @JsonIgnoreProperties("facility")
    private List<Facility> facilities = new ArrayList<>();

    public Withdraw(String id, BigInteger withdrawAmount, Date withdrawTime, String withdrawMethod, String withdrawTarget, List<Facility> facilities) {
        this.id = id;
        this.withdrawAmount = withdrawAmount;
        this.withdrawTime = withdrawTime;
        this.withdrawMethod = withdrawMethod;
        this.withdrawTarget = withdrawTarget;
        this.facilities = facilities;
    }
}
