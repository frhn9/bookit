package com.enigma.bookit.repository;

import com.enigma.bookit.entity.Book;
import com.enigma.bookit.entity.Refund;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RefundRepositoryTest {

    @Autowired
    RefundRepository refundRepository;

    @Autowired
    EntityManager entityManager;

    private Refund refund = new Refund();

    @BeforeEach
    void setup(){
        refund.setId("R01");
        refund.setBook(new Book());
        refund.setRequestRefundTime(LocalDateTime.now());
        refund.setRefundTime(LocalDateTime.now());
        refund.setRefundAmount(BigDecimal.valueOf(1000.00));
        refund.setStatus(false);
    }

    @Test
    void shouldSaveRefund() {
        Refund input = refundRepository.save(refund);
        List<Refund> refunds = new ArrayList<>();
        refunds.add(input);
        assertEquals(input.getRefundAmount(), refund.getRefundAmount());
    }
}