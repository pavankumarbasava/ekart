package com.ekart.cart.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ekart.cart.dtos.CartDto;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="cart_item")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CartItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "cart_id", nullable = false)
	private Cart cart;

	@Column(name = "product_id")
	private Integer  productId;

	@Column(name = "quantity")
    private Integer quantity;
	
	@Column(name = "size")
	private String size;
	
	@Column(name = "price")
	private Double price;

	@Column(name = "discounted_price")
	private Double discountedPrice;
	
	@JsonIgnore
	@Column(name = "user_id")
	private Long userId;

}
