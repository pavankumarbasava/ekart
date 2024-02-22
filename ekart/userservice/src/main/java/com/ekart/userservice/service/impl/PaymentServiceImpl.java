package com.ekart.userservice.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ekart.userservice.entity.Address;
import com.ekart.userservice.entity.PaymentInformation;
import com.ekart.userservice.repository.AddressRepository;
import com.ekart.userservice.repository.PaymentRepository;
import com.ekart.userservice.service.AddressService;
import com.ekart.userservice.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

	private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

	@Autowired
	private PaymentRepository payRepo;

	public List<PaymentInformation> getAllPayments(String name) {
		return payRepo.getAllPayments(name).get();
	}

	public Optional<PaymentInformation> findById(Long paymentId) {
		return payRepo.findById(paymentId);
	}

	public PaymentInformation save(PaymentInformation payment) {
		return payRepo.save(payment);
	}

	public PaymentInformation update(PaymentInformation payment) {
		return payRepo.save(payment);
	}

	public boolean deleteById(Long addressId) {

		try {
			payRepo.deleteById(addressId);
			return true;
		} catch (Exception e) {
			logger.error("Exception while deleting address ", e);
			return false;
		}
	}

}
