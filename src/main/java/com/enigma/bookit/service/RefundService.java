package com.enigma.bookit.service;

import com.enigma.bookit.dto.RefundDTO;
import com.enigma.bookit.dto.RefundSearchDTO;
import com.enigma.bookit.entity.Refund;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface RefundService {
    RefundDTO applyRefund(Refund refund);
    RefundDTO acceptRefund(String id, BigDecimal amount);
    RefundDTO getById(String id);
    void deleteById(String id);
    Page<RefundDTO> getAllRefund(Pageable pageable, RefundSearchDTO refundSearchDTO);
}
