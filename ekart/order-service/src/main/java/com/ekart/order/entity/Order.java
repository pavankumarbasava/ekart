package com.ekart.order.entity;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "orders")
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

  
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Column(name="order_date")
    private LocalDateTime orderDate;

    @Column(name="delivery_date")
    private LocalDateTime deliveryDate;


    @Column(name="total_price")
    private Double totalPrice;
    
    @Column(name="total_discounted_price")
    private Double totalDiscountedPrice;
    
    @Column(name="discounte_amount")
    private Double discounte;

    @Column(name="order_status")
    private OrderStatus orderStatus;
    
    @Column(name="total_items")
    private Double totalItem;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="address")
    private Address shippingAddress;
    
    @Column(name="payment_id")
    private Long paymentId;
    

    @Column(name="user_id")
    private Long user;
    

    @Column(name="order_code")
    private String orderCode;
    
    
   

   
    
}
