package com.enigma.bookit.controller;

import com.enigma.bookit.service.RefundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/refund")
public class RefundController {

    @Autowired
    RefundService refundService;


}
