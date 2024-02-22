package com.ekart.commonservice.events;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderCompletedEvent {
    private String orderCode;
    private String orderStatus;
//    private String token;
}
