package com.ekart.commonservice.events;

import lombok.Data;

@Data
public class OrderCancelledEvent {
    private String orderCode;
    private String orderStatus;
//    private String token;
}
