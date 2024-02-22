package com.ekart.commonservice.commands;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
public class CancelOrderCommand {

    @TargetAggregateIdentifier
    private String orderCode;
    private String orderStatus = "CANCELLED";
}
