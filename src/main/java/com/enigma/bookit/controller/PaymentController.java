package com.enigma.bookit.controller;

import com.enigma.bookit.entity.Payment;
import com.enigma.bookit.service.PaymentService;
import com.enigma.bookit.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @PostMapping()
    public ResponseEntity<Response<Payment>> createPayment(@RequestBody Payment payment){
        Response<Payment> response = new Response<>();
        String message = "Payment is inserted";
        response.setMessage(message);
        response.setData(paymentService.save(payment));
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PutMapping("/pay/{id}")
    public ResponseEntity<Response<Payment>> payPayment(@PathVariable String id){
        Response<Payment> response = new Response<>();
        String message = "Payment is updated";
        response.setMessage(message);
        response.setData(paymentService.pay(id));
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("/{id}")
    public Payment getById(@PathVariable String id){
        return paymentService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable String id){
        paymentService.deleteById(id);
    }

//    @GetMapping()
//    public Page<Payment> getPaymentByPage(
//            @RequestParam(name="page", defaultValue="0") Integer page,
//            @RequestParam(name="size", defaultValue="3") Integer size,
//            @RequestParam(name="sortby", defaultValue="id") String sortBy,
//            @RequestParam(name="direction", defaultValue="ASC") String direction){
//        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
//        Pageable pageable = PageRequest.of(page, size, sort);
//        return paymentService.getAllPerPage(pageable);
//    }
}
