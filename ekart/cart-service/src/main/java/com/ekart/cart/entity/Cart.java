package com.ekart.cart.entity;

import java.util.Set;

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

@Entity
@Table(name="cart")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   
    @Column(name = "user_id", nullable = false ,unique = true)
    private Long user;
    
    @Column(name = "cart_code", nullable = false ,unique = true)
    private String cartCode;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
//   / @JoinColumn(name = "cart_items")
    private Set<CartItem> cartItems ;

    @Column(name = "total_price")
    private Double totalPrice;
    
    @Column(name="total_item")
    private Double totalItem;
    
    @Column(name = "subtotal")
    private Double totalDiscountedPrice;
    
    @Column(name = "discounte_amount")
    private Double discounte;
  
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address")
    private Address address;
    
	

}
