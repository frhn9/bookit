package com.enigma.bookit.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CallbackDTO {
    private String id;
    private String external_id;
    private String user_id;
    private String status;
    private String merchant_name;
    private String merchant_profile_picture_url;
    private Double amount;
    private String payer_email;
    private String description;
    private Double fees_paid_amount;
    private Double adjusted_received_amount;
    private String bank_code;
    private String retail_outlet_name;
    private String ewallet_type;
    private String on_demand_link;
    private String recurring_payment_id;
    private Double paid_amount;
    private String updated;
    private String created;
    private String mid_label;
    private String failure_redirect_url;
    private String paid_at;
    private String credit_card_charge_id;
}
