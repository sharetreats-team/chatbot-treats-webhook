package com.sharetreats.chatbot.module.entity;

import com.sharetreats.chatbot.module.option.Status;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Table(name = "brand")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Brand {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String image;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Builder
    public Brand(Long id, String name, String image, Status status) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.status = status;
    }

    public void InactivateStatus() {
        this.status = Status.INACTIVE;
    }

    public void ActivateStatus() {
        this.status = Status.ACTIVE;
    }
}
