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
    public Refund applyRefund(Refund refund) {
        refund.setRequestRefundTime(new Date(System.currentTimeMillis()));
        return refundRepository.save(refund);
    }
}
