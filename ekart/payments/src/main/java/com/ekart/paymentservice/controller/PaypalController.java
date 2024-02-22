//package com.ekart.paymentservice.controller;
//
//import java.net.URI;
//import java.net.URISyntaxException;
//
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.ekart.paymentservice.model.Order;
//import com.ekart.paymentservice.response.ResponseMessage;
//import com.ekart.paymentservice.service.PaypalService;
//import com.paypal.api.payments.Links;
//import com.paypal.api.payments.Payment;
//import com.paypal.base.rest.PayPalRESTException;
//
//@RestController
//@RequestMapping("/api/payment")
//public class PaypalController {
//
//	@Autowired
//	PaypalService service;
//
//	public static final String SUCCESS_URL = "pay/success";
//	public static final String CANCEL_URL = "pay/cancel";
//
////	@GetMapping("/")
////	public String home() {
////		return "home";
////	}
//
//	@GetMapping("/paypal")
//	public ResponseEntity<?> payment(@RequestParam("cartId") String cartId) throws URISyntaxException {
//		try {
//		
//			return paymentProcess( cartId);
//			
//		} catch (PayPalRESTException e) {
//		
//			e.printStackTrace();
//			 return new ResponseEntity<>(new ResponseMessage("There is an error while redirecting to  paypal"), HttpStatus.SEE_OTHER);
//		}
//		 
//		
//	}
//
//
//	private ResponseEntity<?> paymentProcess(String cartId) throws PayPalRESTException, URISyntaxException {
//		Payment payment= service.createPayment(price, "USD", "paypal",
//				"sale", "payment for order  "+orderid, "http://localhost:8011/" + CANCEL_URL,
//				"http://localhost:8011/" + SUCCESS_URL);
//		HttpHeaders httpHeaders=null;
//		for(Links link:payment.getLinks()) {
//			if(link.getRel().equals("approval_url")) {
//				 URI redirectUrl = new URI(link.getHref());
//				     httpHeaders = new HttpHeaders();
//				    httpHeaders.setLocation(redirectUrl);
//				return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
//			}
//		}
//		return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
//	}
//	
//
//	   @GetMapping(value = CANCEL_URL)
//	    public ResponseMessage cancelPay() {
//	        return new ResponseMessage("Your payment is cancled");
//	    }
//
//	    @GetMapping(value = SUCCESS_URL)
//	    public ResponseEntity<?> successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) throws URISyntaxException {
//	        try {
//	            Payment payment = service.executePayment(paymentId, payerId);
//	            System.out.println(payment.toJSON());
//	            if (payment.getState().equals("approved")) {
//	            	 URI redirectUrl = new URI("https://www.youtube.com/");
//					    HttpHeaders httpHeaders = new HttpHeaders();
//					    httpHeaders.setLocation(redirectUrl);
//					    return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
//	            }
//	        } catch (PayPalRESTException e) {
//	         System.out.println(e.getMessage());
//	        }
//	        return new ResponseEntity<>(new ResponseMessage("There is an error while redirecting to  paypal"), HttpStatus.SEE_OTHER);
//	    }
//
//}
