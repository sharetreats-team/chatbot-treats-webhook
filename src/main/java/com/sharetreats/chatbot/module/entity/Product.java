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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column
    private String image;
    @Column(nullable = false)
    private String status;
    @Column
    private String description;
    @Column(nullable = false)
    private Integer price;
    @Column(name = "discount_price")
    private Integer discountPrice;
    @Column(name = "discount_shop")
    private String discountShop;
    @Column(name = "brand_name")
    private String brandName;

}
