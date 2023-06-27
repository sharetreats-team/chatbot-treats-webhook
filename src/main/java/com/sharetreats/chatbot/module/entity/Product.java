package com.sharetreats.chatbot.module.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "product")
@Getter
@NoArgsConstructor
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    public String getBrandName() {
        return brand.getName();
    }
}
