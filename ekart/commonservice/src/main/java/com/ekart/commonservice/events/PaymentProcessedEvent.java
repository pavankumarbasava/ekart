package com.ekart.commonservice.events;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentProcessedEvent {
    private String paymentCode;
    private String orderCode;
    private Long useId;
}
