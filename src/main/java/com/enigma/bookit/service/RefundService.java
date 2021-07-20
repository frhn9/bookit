package com.enigma.bookit.service;

import com.enigma.bookit.entity.Refund;

public interface RefundService {
    Refund save(Refund refund);
    Refund getById(String id);
    void deleteById(String id);
}
