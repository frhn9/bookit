package com.enigma.bookit.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @NoArgsConstructor
public class InvoiceResponseDTO {
    private String id;
    private String externalId;
    private String invoiceUrl;
    private String status;
    private String merchantName;
    private Number amount;
    private String email;
    private String paidAt;
    private Number paidAmount;
    private String created;
    private String updated;
    private String currency;

    public InvoiceResponseDTO(String id, String externalId, String invoiceUrl, String status, String merchantName, Number amount, String email, String paidAt, Number paidAmount, String created, String updated, String currency) {
        this.id = id;
        this.externalId = externalId;
        this.invoiceUrl = invoiceUrl;
        this.status = status;
        this.merchantName = merchantName;
        this.amount = amount;
        this.email = email;
        this.paidAt = paidAt;
        this.paidAmount = paidAmount;
        this.created = created;
        this.updated = updated;
        this.currency = currency;
    }
}
