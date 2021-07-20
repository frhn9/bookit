package com.enigma.bookit.service;

import com.enigma.bookit.entity.Payment;

public interface PaymentService {
    Payment save(Payment payment);
    Payment getById(String id);
    void deleteById(String id);
}
