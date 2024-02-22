//package com.ekart.paymentservice.command.api.aggregate;
//
//import org.axonframework.commandhandling.CommandHandler;
//import org.axonframework.eventsourcing.EventSourcingHandler;
//import org.axonframework.modelling.command.AggregateIdentifier;
//import org.axonframework.modelling.command.AggregateLifecycle;
//import org.axonframework.spring.stereotype.Aggregate;
//import org.springframework.beans.BeanUtils;
//
//import com.ekart.commonservice.commands.CancelPaymentCommand;
//import com.ekart.commonservice.commands.ValidatePaymentCommand;
//import com.ekart.commonservice.events.PaymentCancelledEvent;
//import com.ekart.commonservice.events.PaymentProcessedEvent;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Aggregate
//@Slf4j
//public class PaymentAggregate {
//
//    @AggregateIdentifier
//    private String paymentCode;
//    private String orderCode;
//    private String paymentStatus;
//    private Long userId;
//
//    public PaymentAggregate() {
//    }
//
//    @CommandHandler
//    public PaymentAggregate(ValidatePaymentCommand validatePaymentCommand) {
//        //Validate the Payment Details
//        // Publish the Payment Processed event
//        log.info("Executing ValidatePaymentCommand for " +
//                "Order Id: {} and Payment Id: {}",
//                validatePaymentCommand.getOrderCode(),
//                validatePaymentCommand.getPaymentCode());
//
//        PaymentProcessedEvent paymentProcessedEvent
//                = new PaymentProcessedEvent(
//                validatePaymentCommand.getPaymentCode(), validatePaymentCommand.getOrderCode()
//        ,validatePaymentCommand.getUserId());
//
//        AggregateLifecycle.apply(paymentProcessedEvent);
//
//        log.info("PaymentProcessedEvent Applied");
//    }
//
//    @EventSourcingHandler
//    public void on(PaymentProcessedEvent event) {
//        this.paymentCode = event.getPaymentCode();
//        this.orderCode = event.getOrderCode();
//        this.userId=event.getUseId();
//    }
//
//    @CommandHandler
//    public void handle(CancelPaymentCommand cancelPaymentCommand) {
//        PaymentCancelledEvent paymentCancelledEvent
//                = new PaymentCancelledEvent();
//        BeanUtils.copyProperties(cancelPaymentCommand,
//                paymentCancelledEvent);
//
//        AggregateLifecycle.apply(paymentCancelledEvent);
//    }
//
//    @EventSourcingHandler
//    public void on(PaymentCancelledEvent event) {
//        this.paymentStatus = event.getPaymentStatus();
//    }
//}
