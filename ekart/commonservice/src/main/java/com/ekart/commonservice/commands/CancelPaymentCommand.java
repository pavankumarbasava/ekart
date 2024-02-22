package com.ekart.commonservice.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.ekart.commonservice.dto.PaymentStatus;

import lombok.Value;

@Value
public class CancelPaymentCommand {

    @TargetAggregateIdentifier
    private String paymentCode;
    private String orderCode;
    private PaymentStatus paymentStatus = PaymentStatus.CANCELLED;
}
