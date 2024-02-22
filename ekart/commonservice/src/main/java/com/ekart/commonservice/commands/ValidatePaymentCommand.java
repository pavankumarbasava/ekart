package com.ekart.commonservice.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.ekart.commonservice.model.CardDetails;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidatePaymentCommand {

    @TargetAggregateIdentifier
    private String paymentCode;
    private String orderCode;
    private Long userId;
    private Double payAmount;
}
