package com.ekart.cart.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="address")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Address {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name="first_name")
	private String firstName;
	
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="country")
	private String country;
	
	 @Column(name = "street_address_part1")
	   private String streetAddressPart1;
	
    @Column(name = "street_address_part2")
    private String streetAddressPart2;
    
    @Column(name = "landmark")
    private String landmark;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "zip_code")
    private String zipCode;
    
    @Column(name = "mobile")
    private String mobile;
    
    @OneToOne(mappedBy = "address")
    private Cart cart;
    

}
