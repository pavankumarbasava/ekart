package com.ekart.order.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="order_item")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class OrderItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "order_id", nullable = false)
	private Order order;
	
	@Column(name = "product_id")
	private Long productId;
	
	@Column(name = "quantity")
	private Integer quantity;
	
	@Column(name = "price")
	private Double price;
	
	@Column(name = "size")
	private String size;
	
	@Column(name = "discounted_price")
	private Double discountedPrice;
	
	
	@Column(name = "delivery_date")
	private LocalDateTime deliveryDate;
	
	
}
