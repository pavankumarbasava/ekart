//package com.ekart.paymentservice.command.api.events;
//
//import java.util.Date;
//
//import javax.persistence.Column;
//
//import org.axonframework.eventhandling.EventHandler;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//
//import com.ekart.commonservice.events.PaymentCancelledEvent;
//import com.ekart.commonservice.events.PaymentProcessedEvent;
//import com.ekart.paymentservice.entity.Payment;
//import com.ekart.paymentservice.entity.PaymentStatus;
//import com.ekart.paymentservice.repository.PaymentRepository;
//
//@Component
//public class PaymentsEventHandler {
//
//	@Autowired
//    private PaymentRepository paymentRepository;
//
//  
//
//    @EventHandler
//    public void on(PaymentProcessedEvent event) {
//        Payment payment
//                = Payment.builder()
//                .paymentCode(event.getPaymentCode())
//                .orderCode(event.getOrderCode())
//                .status(PaymentStatus.SUCCESS)
//                .userId(event.getUseId())
//                .type("Paypal")
//                .mode("online")
//                
//                .build();
//           
////        private String transactionCode;
//        
//  
//  //      private String content;
//
//        paymentRepository.save(payment);
//    }
//
//    @EventHandler
//    public void on(PaymentCancelledEvent event) {
//        Payment payment
//                = paymentRepository.findByPaymentCode(event.getPaymentCode());
//
//        payment.setStatus(PaymentStatus.CANCELLED);
//
//        paymentRepository.save(payment);
//    }
//}
