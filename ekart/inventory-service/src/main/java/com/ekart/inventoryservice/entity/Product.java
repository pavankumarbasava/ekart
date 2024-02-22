package com.ekart.inventoryservice.entity;

//import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = {"category"})
@Data
@Builder
@Entity
@Table(name = "products")
public final class Product extends AbstractMappedEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Integer id;

    @Column(name = "product_title")
    private String productTitle;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name="description")
    private String description;
    
    @Column(unique = true)
    private String sku;
    
    @Column(name = "discounted_price")
    private Double discountedPrice;
    
    @Column(name="discount_persent")
    private Double discountPersent;
    
    @Column(name = "price", columnDefinition = "decimal")
    private Double price;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "brand")
    private String brand;

    @Column(name = "color")
    private String color;
    
    @Column(name = "num_ratings")
    private Integer numRatings;
    
    @Embedded
    @ElementCollection
    @Column(name = "sizes")
    private Set<Size> sizes=new HashSet<>();
    
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;
    
//    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true)
//    private List<Rating>ratings=new ArrayList<>();
    
    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ReviewRating> reviewRating =new ArrayList<>();

    @Column(name = "stock")
    private Boolean stock;
    
}
