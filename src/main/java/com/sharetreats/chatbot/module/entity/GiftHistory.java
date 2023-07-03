package com.sharetreats.chatbot.module.entity;

import com.sharetreats.chatbot.module.option.GiftAvailability;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Table(name = "gift_history")
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GiftHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gift_history_id")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private GiftAvailability availability;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime expirationDate;

    @Column(nullable = false)
    private String giftCode;

    @Column(nullable = false)
    private String receiverName;

    @Column(nullable = false)
    private String receiverEmail;

    private String message;

    private int price;

    @ManyToOne
    private Account sender;

    @ManyToOne
    private Product product;

    private GiftHistory(String receiverName, String receiverEmail, String message, int price, Account sender, Product product) {
        this.availability = GiftAvailability.AVAILABLE;
        this.giftCode = initGiftCode();
        this.receiverName = receiverName;
        this.receiverEmail = receiverEmail;
        this.message = message;
        this.price = price;
        this.sender = sender;
        this.product = product;
    }

    public static GiftHistory of(String receiverName, String receiverEmail, String message, Integer price, Account sender, Product product) {
        return new GiftHistory(receiverName, receiverEmail,message, price, sender,product);
    }

    /**
     * 현재는 랜덤 문자열로 선물코드를 저장합니다.
     * @return 선물 코드
     */
    private String initGiftCode() {
        return UUID.randomUUID().toString();
    }

    /**
     * 선물코드 초기 발급 시점으로 1개월 이후 만료됩니다.
     * @return 만료날짜
     */
    private LocalDateTime initExpirationDate() {
        return LocalDateTime.MAX.plusMonths(1);
    }
}
