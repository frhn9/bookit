package com.enigma.bookit.service.implementation;

import com.enigma.bookit.entity.Refund;
import com.enigma.bookit.repository.RefundRepository;
import com.enigma.bookit.service.RefundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RefundServiceImpl implements RefundService {

    @Autowired
    RefundRepository refundRepository;

    //Customer apply for refund
    @Override
    public Refund save(Refund refund) {
        refund.setRequestRefundTime(new Date(System.currentTimeMillis()));
        return refundRepository.save(refund);
    }

    @Override
    public Refund getById(String id) {
        return refundRepository.getById(id);
    }

    @Override
    public void deleteById(String id) {
        refundRepository.deleteById(id);
    }
}
