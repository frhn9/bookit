package com.enigma.bookit.controller;

import com.enigma.bookit.constant.ApiUrlConstant;
import com.enigma.bookit.constant.SuccessMessageConstant;
import com.enigma.bookit.dto.*;
import com.enigma.bookit.entity.PackageChosen;
import com.enigma.bookit.entity.Payment;
import com.enigma.bookit.service.PaymentService;
import com.enigma.bookit.service.UserService;
import com.enigma.bookit.utils.PageResponseWrapper;
import com.enigma.bookit.utils.Response;
import com.xendit.Xendit;
import com.xendit.exception.XenditException;
import com.xendit.model.Invoice;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(ApiUrlConstant.PAYMENT)
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @Autowired
    UserService userService;

    private ModelMapper modelMapper = new ModelMapper();

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
    @PostMapping()
    public ResponseEntity<Response<InvoiceResponseDTO>> createPayment(@RequestBody Payment payment) throws XenditException {
        Response<InvoiceResponseDTO> response = new Response<>();

        String userId = payment.getUser().getId();
        UserDto user = userService.getById(userId);

        PaymentDTO paymentDTO = paymentService.save(payment);
        Xendit.apiKey = "xnd_development_1sRaZoJjfer9Xjmqb44h96lv0LOxPbcVft3VGGJCuA7fg1wjZ7LOabMDDxOxR0";
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("external_id", (paymentDTO.getId()));
            params.put("amount", paymentDTO.getAmount());
            params.put("payer_email", user.getEmail());
            params.put("description", (paymentDTO.getPackageChosen()));

            Invoice invoice = Invoice.create(params);
            InvoiceResponseDTO invoiceResponse = modelMapper.map(invoice, InvoiceResponseDTO.class);

            response.setData(invoiceResponse);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        }catch (
                XenditException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
    @GetMapping("/{id}")
    public PaymentDTO getById(@PathVariable String id){
        return paymentService.getById(id);
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable String id){
        paymentService.deleteById(id);
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
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

    @PostMapping("/checkout")
    public ResponseEntity<Response<?>> pay (@RequestBody CallbackDTO callbackDTO){
        Response<PaymentDTO> response = new Response<>();
        String message = "Payment is updated";
        response.setMessage(message);
        response.setData(paymentService.pay(callbackDTO));
        System.out.println(callbackDTO.getExternal_id());
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PreAuthorize("hasRole('CUSTOMER') ")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
    @PostMapping("/extend/{bookId}")
    public ResponseEntity<Response<InvoiceResponseDTO>> extendBook(@PathVariable("bookId") String bookId,
                           @RequestBody PackageChosen packageChosen){
        Response<InvoiceResponseDTO> response = new Response<>();
        PaymentDTO paymentDTO = paymentService.extendBook(bookId, packageChosen);

        String customerId = paymentDTO.getUser().getId();
        UserDto user = userService.getById(customerId);
        Xendit.apiKey = "xnd_development_1sRaZoJjfer9Xjmqb44h96lv0LOxPbcVft3VGGJCuA7fg1wjZ7LOabMDDxOxR0";
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("external_id", (paymentDTO.getId()));
            params.put("amount", paymentDTO.getAmount());
            params.put("payer_email", user.getEmail());
            params.put("description", (paymentDTO.getPackageChosen()));

            Invoice invoice = Invoice.create(params);
            InvoiceResponseDTO invoiceResponse = modelMapper.map(invoice, InvoiceResponseDTO.class);

            response.setData(invoiceResponse);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        }catch (
                XenditException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}
