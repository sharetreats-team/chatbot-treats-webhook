package com.sharetreats.chatbot.module.entity;

import com.sharetreats.chatbot.module.option.DiscountType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "discount_code")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiscountCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "discount_code_id")
    private Long id;
    @Column(nullable = false)
    private String code;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DiscountType type;
    @Column(nullable = false)
    private int discountPrice;

    @Builder
    public DiscountCode(String code, DiscountType type, int discountPrice) {
        this.code = code;
        this.type = type;
        this.discountPrice = discountPrice;
    }
}
