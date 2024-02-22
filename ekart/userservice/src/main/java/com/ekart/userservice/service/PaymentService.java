package com.ekart.userservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ekart.userservice.entity.Address;
import com.ekart.userservice.entity.PaymentInformation;


public interface PaymentService {

	public List<PaymentInformation> getAllPayments(String name);
}
