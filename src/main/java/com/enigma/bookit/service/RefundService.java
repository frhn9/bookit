package com.enigma.bookit.service;

import com.enigma.bookit.dto.RefundSearchDTO;
import com.enigma.bookit.entity.Refund;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface RefundService {
    Refund applyRefund(Refund refund);
    Refund acceptRefund(String id, BigDecimal amount);
    Refund getById(String id);
    void deleteById(String id);
    Page<Refund> getAllRefund(Pageable pageable, RefundSearchDTO refundSearchDTO);
}
