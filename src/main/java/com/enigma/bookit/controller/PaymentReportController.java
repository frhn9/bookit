package com.enigma.bookit.controller;

import com.enigma.bookit.dto.PaymentSearchDTO;
import com.enigma.bookit.entity.Refund;
import com.enigma.bookit.service.PaymentReportService;
import com.enigma.bookit.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gen")
public class PaymentReportController {
    @Autowired
    private PaymentReportService paymentReportService;

    @GetMapping("/report")
    public ResponseEntity<Response<?>> generateReport(@RequestBody PaymentSearchDTO paymentSearchDTO){
        paymentReportService.generateReport(paymentSearchDTO);
        Response<?> response = new Response<>();
        response.setMessage("OK");
        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"payment.pdf"+"\"")
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}
