package com.sharetreats.chatbot.module.entity;

import com.sharetreats.chatbot.module.option.Subscription;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Table(name = "account")
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account implements Persistable<String> {
    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String avatar;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Subscription subscription = Subscription.SUBSCRIBE;

    @Column(nullable = false)
    private int point;

    @CreatedDate
    private LocalDateTime createdAt;

    @Builder
    public Account(String id, String name, String avatar, int point) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.point = point;
    }

    @Override
    public boolean isNew() {
        return createdAt ==null;
    }

    public void changeReSubscribed(Subscription targetSubscription) {
        if (targetSubscription.getKey().equals(Subscription.UNSUBSCRIBE.getKey())) {
            this.subscription = Subscription.SUBSCRIBE;
        }
    }

    public void changeUnsubscribed() {
        this.subscription = Subscription.UNSUBSCRIBE;
    }

    public static Account of(String id, String name, String avatar, int point) {
        return new Account(id, name, avatar, point);
    }

    public void decreasePoint(int price) {
        this.point -= price;
    }
}
