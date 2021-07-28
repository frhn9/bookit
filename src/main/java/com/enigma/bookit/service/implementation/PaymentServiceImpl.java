package com.enigma.bookit.service.implementation;

import com.enigma.bookit.entity.Payment;
import com.enigma.bookit.repository.PaymentRepository;
import com.enigma.bookit.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    PaymentRepository paymentRepository;

    @Override
    //harus cek jam ini udah dipesan atau belum
    public Payment save(Payment payment) {
        payment.setDueTime(new Date(System.currentTimeMillis() + 3600000));
        //ambil price dari facility, tergantung gimana caranya bingung uwu, mungkin if if
        return paymentRepository.save(payment);
    }

    @Override
    public Payment getById(String id) {
        validatePresent(id);
        return paymentRepository.findById(id).get();
    }

    @Override
    public void deleteById(String id) {
        validatePresent(id);
        paymentRepository.deleteById(id);
    }

    @Override
    //harus cek jam ini udah dipesan atau belum dari tabel book
    public Payment pay(String id) {
        validatePresent(id);
        Payment payment = getById(id);
        payment.setPaymentStatus(true);
        payment.setPaymentDate(new Date(System.currentTimeMillis()));
        return paymentRepository.save(payment);
        //bikin book
    }

    @Override
    public Page<Payment> getAllPerPage(Pageable pageable) {
        return paymentRepository.findAll(pageable);
    }

    private void validatePresent(String id){
        if(!paymentRepository.findById(id).isPresent()){
            String message = "id not found";
            throw new DataNotFoundException(message);
        }
    }
}
