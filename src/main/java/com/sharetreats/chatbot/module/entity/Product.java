package com.sharetreats.chatbot.module.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "product")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String image;
    @Column(nullable = false)
    private String status;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private Integer price;
    @Column(name = "discount_price", nullable = false)
    private Integer discountPrice;
    @Column(name = "discount_shop", nullable = false)
    private String discountShop;
    @Column(name = "brand_name", nullable = false)
    private String brandName;

}
