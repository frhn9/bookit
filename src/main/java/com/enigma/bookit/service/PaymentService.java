package com.enigma.bookit.service;

import com.enigma.bookit.dto.PaymentDTO;
import com.enigma.bookit.dto.PaymentSearchDTO;
import com.enigma.bookit.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentService {
    PaymentDTO save(Payment payment);
    PaymentDTO getById(String id);
    void deleteById(String id);
    PaymentDTO pay(String id);
    Page<PaymentDTO> getAllPerPage(Pageable pageable, PaymentSearchDTO paymentSearchDTO);
}
