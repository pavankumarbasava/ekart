package com.ekart.paymentservice.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@ToString
@Table(name = "payment_transaction")
public final class Payment extends AbstractMappedEntity implements Serializable {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "order_code")
    private String orderCode;

    @Column(name = "payment_code")
    private String paymentCode;
    
    @Column(name = "type")
    private String type;
    
    @Column(name = "mode")
    private String mode;
    
    @Column(name = "status")
    private PaymentStatus status;
    
    @Column(name = "transaction_code")
    private String transactionCode;
    
    @Column(name = "conetent")
    private String content;
    
    @Column(name = "cardholder_name")
    private String cardholderName;

	@Column(name = "card_number")
	private String cardNumber;
	
	@Column(name = "card_type")
	private String cardType;
	
	

	@Column(name = "expiration_month")
	private String expirationMonth;

	@Column(name = "expiration_year")
	private String expirationYear;

	@Column(name = "cvv")
	private String cvv;



}