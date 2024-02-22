package com.ekart.paymentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ekart.paymentservice.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

	
	@Query("select p from Payment p where p.paymentCode=:paymentCode")
	Payment findByPaymentCode(@Param("paymentCode") String paymentCode);


	

}
