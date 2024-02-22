package com.ekart.productservice.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.ekart.productservice.constants.AppConstant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.AllArgsConstructor;
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
@Table(name = "wishlist")
public class WishList {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
     @Column(name = "user_id", nullable = false)
     private Long userId;
     
    
     @Column(name = "product_id", nullable = false)
     private Long  products ;

     
     @Column(name = "like_date", nullable = false)
     @JsonSerialize(using = LocalDateTimeSerializer.class)
     @JsonDeserialize(using = LocalDateTimeDeserializer.class)
     @JsonFormat(pattern = AppConstant.LOCAL_DATE_TIME_FORMAT, shape = Shape.STRING)
     @DateTimeFormat(pattern = AppConstant.LOCAL_DATE_TIME_FORMAT)
    private  LocalDateTime likeDate;
	    
}

