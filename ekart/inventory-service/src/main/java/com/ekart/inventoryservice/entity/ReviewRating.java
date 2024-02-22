package com.ekart.inventoryservice.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Builder
@Table(name = "reviewRating")
public class ReviewRating {

	@Id

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_id", nullable = false)
	private Long userId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	@Column(name = "rating")
	private double rating;

	@Column(name = "review")
	private String review;
	
	@Column(name = "user_name")
	private String userName;

	private LocalDateTime ratingCreatedAt;

	private LocalDateTime reviewCreatedAt;

	private LocalDateTime ratingUpdatedAt;

	private LocalDateTime reviewUpdatedAt;

	

}
