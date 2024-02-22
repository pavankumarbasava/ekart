package com.ekart.userservice.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "payment_information")
@Getter
@Setter

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentInformation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

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

	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private User user;

}
