package com.enigma.bookit.controller;

import com.enigma.bookit.service.PaymentReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gen")
public class PaymentReportController {
    @Autowired
    private PaymentReportService paymentReportService;

    @GetMapping("/report")
    public String generateReport(){
        return paymentReportService.generateReport();
    }
}
