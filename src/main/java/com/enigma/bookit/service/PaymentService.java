package com.enigma.bookit.service;

import com.enigma.bookit.dto.PaymentSearchDTO;
import com.enigma.bookit.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentService {
    Payment save(Payment payment);
    Payment getById(String id);
    void deleteById(String id);
    Payment pay(String id);
    Page<Payment> getAllPerPage(Pageable pageable, PaymentSearchDTO paymentSearchDTO);
}
