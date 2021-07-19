package com.enigma.bookit.service.implementation;

import com.enigma.bookit.entity.Payment;
import com.enigma.bookit.repository.PaymentRepository;
import com.enigma.bookit.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    PaymentService paymentService;

    @Override
    public Payment savePayment(Payment payment) {
        return null;
    }
}
