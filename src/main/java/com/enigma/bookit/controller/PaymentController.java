package com.enigma.bookit.controller;

import com.enigma.bookit.constant.ApiUrlConstant;
import com.enigma.bookit.constant.SuccessMessageConstant;
import com.enigma.bookit.dto.CustomerDto;
import com.enigma.bookit.dto.InvoiceResponseDTO;
import com.enigma.bookit.dto.PaymentDTO;
import com.enigma.bookit.dto.PaymentSearchDTO;
import com.enigma.bookit.entity.user.Customer;
import com.enigma.bookit.entity.Payment;
import com.enigma.bookit.entity.Refund;
import com.enigma.bookit.service.CustomerService;
import com.enigma.bookit.service.PaymentService;
import com.enigma.bookit.utils.PageResponseWrapper;
import com.enigma.bookit.utils.Response;
import com.xendit.Xendit;
import com.xendit.exception.XenditException;
import com.xendit.model.Invoice;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(ApiUrlConstant.PAYMENT)
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @Autowired
    CustomerService customerService;

    @PostMapping()
    public ResponseEntity<Response<PaymentDTO>> createPayment(@RequestBody Payment payment){
        Response<PaymentDTO> response = new Response<>();
        String message = "Payment is inserted";
        response.setMessage(message);
        response.setData(paymentService.save(payment));
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PutMapping("/pay/{id}")
    public ResponseEntity<Response<PaymentDTO>> payPayment(@PathVariable String id){
        Response<PaymentDTO> response = new Response<>();
        String message = "Payment is updated";
        response.setMessage(message);
        response.setData(paymentService.pay(id));
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("/{id}")
    public PaymentDTO getById(@PathVariable String id){
        return paymentService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable String id){
        paymentService.deleteById(id);
    }

    @GetMapping()
    public PageResponseWrapper<PaymentDTO> getPaymentByPage(
            @RequestBody PaymentSearchDTO paymentSearchDTO,
            @RequestParam(name="page", defaultValue="0") Integer page,
            @RequestParam(name="size", defaultValue="3") Integer size,
            @RequestParam(name="sortby", defaultValue="id") String sortBy,
            @RequestParam(name="direction", defaultValue="ASC") String direction){
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Integer code = HttpStatus.OK.value();
        String status = HttpStatus.OK.name();
        String message = SuccessMessageConstant.GET_DATA_SUCCESSFUL;
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<PaymentDTO> paymentPage = paymentService.getAllPerPage(pageable, paymentSearchDTO);
        return new PageResponseWrapper<PaymentDTO>(code, status, message,paymentPage);
    }

    @PostMapping("/payXendit}")
    public ResponseEntity<Response<?>> pay (@PathVariable String id){
        ModelMapper modelMapper = new ModelMapper();
        PaymentDTO payment = new PaymentDTO();
        CustomerDto customer = new CustomerDto();
        payment = paymentService.getById(id);
        String customerId = payment.getCustomer().getId();
        customer = customerService.getById(customerId);
        Xendit.apiKey = "xnd_public_development_5KWXDH4AKryvnfF04ljXCD811g9vyglVDV8Xv8Y3PQwsrqIJkdtbFtoqUwZ";
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("external_id", (payment.getId()));
            params.put("amount", payment.getAmount());
            params.put("payer_email", customer.getEmail());
            params.put("description", (payment.getPackageChosen()));

            Invoice invoice = Invoice.create(params);

            InvoiceResponseDTO invoiceResponse = modelMapper.map(invoice, InvoiceResponseDTO.class);

            PaymentDTO paymentDTO = new PaymentDTO();
            paymentDTO = paymentService.pay(payment.getId());

            Response<PaymentDTO> response = new Response<>();
            String message = "Payment is updated";
            response.setMessage(message);
            response.setData(paymentDTO);

            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        } catch (
                XenditException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
//    @GetMapping("/callback")
//    void getCallback(@RequestBody)
}
