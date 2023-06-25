package com.sharetreats.chatbot.product.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "products")
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
    @Column
    private Integer discountPrice;
    @Column
    private String discountShop;
    @Column(nullable = false)
    private String brandName;

    public Product(String name, String image, String status, String description, Integer price, Integer discountPrice, String discountShop, String brandName) {
        this.name = name;
        this.image = image;
        this.status = status;
        this.description = description;
        this.price = price;
        this.discountPrice = discountPrice;
        this.discountShop = discountShop;
        this.brandName = brandName;
    }

}
