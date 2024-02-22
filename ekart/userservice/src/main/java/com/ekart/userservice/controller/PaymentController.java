package com.ekart.userservice.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ekart.userservice.entity.PaymentInformation;
import com.ekart.userservice.entity.User;
import com.ekart.userservice.http.HeaderGeneration;
import com.ekart.userservice.model.dto.response.ResponseMessage;
import com.ekart.userservice.service.impl.PaymentServiceImpl;
import com.ekart.userservice.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/api/information/payment")
public class PaymentController {

	private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

	@Autowired
	private PaymentServiceImpl paymentService;

	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private HeaderGeneration headerGenerator;

	@GetMapping(value = "/user/current/getAllPayments")
	public ResponseEntity<?> getAllPaymentList(Principal principle) {// , @RequestHeader(name = "Authorization") String
																	// authorizationToken) {
		String userName = principle.getName();
		List<PaymentInformation> allPayments = paymentService.getAllPayments(userName);
		try {

			if (!allPayments.isEmpty()) {

				return new ResponseEntity<>(allPayments, headerGenerator.getHeadersForSuccessGetMethod(),
						HttpStatus.OK);
			}
		} catch (Exception e) {
			logger.error("There is an exception while retriving payment ", e);

		}
		return new ResponseEntity<>(new ResponseMessage("There are no payment for this user"),
				headerGenerator.getHeadersForError(), HttpStatus.NOT_FOUND);

	}

	@GetMapping("/get/{paymentId}")
	public ResponseEntity<?> findById(@PathVariable("paymentId") final Long paymentId) {
		return ResponseEntity.ok(this.paymentService.findById(paymentId).get());
	}

	@PostMapping(value = "/save")
	public ResponseEntity<PaymentInformation> savePayment(@RequestBody  PaymentInformation payment, Principal principle) {
		PaymentInformation paymentInfo=null;
		try {
			User user = userService.getUserFromUserName(principle.getName());
			payment.setUser(user);
			
//			   String date = "2029-05-13";
//		        LocalDate localDate = LocalDate.parse(date);
//			payment.setExpirationDate(localDate);
			paymentInfo = paymentService.save(payment);

		} catch (Exception e) {
			logger.error("Exception while saving payment ", e);

			return new ResponseEntity<>(paymentInfo, headerGenerator.getHeadersForError(),
					HttpStatus.BAD_REQUEST);
		}
//		return ResponseEntity.ok(this.);

		return new ResponseEntity<>(paymentInfo, headerGenerator.getHeadersForSuccessGetMethod(),
				HttpStatus.OK);
	}

	@PutMapping(value = "/update")
	public ResponseEntity<PaymentInformation> update(@RequestBody final PaymentInformation payment, Principal principle) {

		User user = userService.getUserFromUserName(principle.getName());
		payment.setUser(user);
		return ResponseEntity.ok(this.paymentService.update(payment));
	}

	@PutMapping("update/{paymentId}")
	public ResponseEntity<PaymentInformation> update(@PathVariable("paymentId") Long paymentId,
			@RequestBody final PaymentInformation payment,Principal principle) {
		
		User user = userService.getUserFromUserName(principle.getName());
		payment.setUser(user);
		payment.setId(paymentId);
		return ResponseEntity.ok(this.paymentService.update(payment));
	}

	@DeleteMapping("delete/{addressId}")
	public ResponseEntity<Boolean> deleteById(@PathVariable("addressId")  Long paymentId) {
		
		return ResponseEntity.ok(this.paymentService.deleteById(paymentId));
	}

//	
//	
//    @GetMapping
//    public ResponseEntity<AddressUserServiceCollectionDtoResponse> findAll() {
//        return ResponseEntity.ok(this.addressClientService.findAll().getBody());
//    }
//
//    @GetMapping("/{addressId}")
//    public ResponseEntity<AddressDto> findById(@PathVariable("addressId") final String addressId) {
//        return ResponseEntity.ok(this.addressClientService.findById(addressId).getBody());
//    }
//
//    @PostMapping
//    public ResponseEntity<AddressDto> save(@RequestBody final AddressDto addressDto) {
//        return ResponseEntity.ok(this.addressClientService.save(addressDto).getBody());
//    }
//
//    @PutMapping
//    public ResponseEntity<AddressDto> update(@RequestBody final AddressDto addressDto) {
//        return ResponseEntity.ok(this.addressClientService.update(addressDto).getBody());
//    }
//
//    @PutMapping("/{addressId}")
//    public ResponseEntity<AddressDto> update(@PathVariable("addressId") final String addressId, @RequestBody final AddressDto addressDto) {
//        return ResponseEntity.ok(this.addressClientService.update(addressDto).getBody());
//    }
//
//    @DeleteMapping("/{addressId}")
//    public ResponseEntity<Boolean> deleteById(@PathVariable("addressId") final String addressId) {
//        return ResponseEntity.ok(this.addressClientService.deleteById(addressId).getBody());
//    }

}