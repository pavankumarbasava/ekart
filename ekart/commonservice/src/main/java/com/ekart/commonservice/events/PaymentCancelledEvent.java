package com.ekart.commonservice.events;


import lombok.Data;

@Data
public class PaymentCancelledEvent {
    private String paymentCode;
    private String orderCode;
    private String paymentStatus;
}
