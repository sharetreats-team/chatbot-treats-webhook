package com.sharetreats.chatbot.module.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RedisHash
public class GiftHistoryRedisHash implements Serializable {

    private String giftCode;
    private String receiverName;
    private String email;
    private String message;
    private Integer point;
    private String senderId;
    private Long productId;

    @Override
    public String toString() {
        return "GiftHistoryRedisHash{" +
                "giftCode='" + giftCode + '\'' +
                ", receiverName='" + receiverName + '\'' +
                ", email='" + email + '\'' +
                ", message='" + message + '\'' +
                ", point=" + point +
                ", senderId='" + senderId + '\'' +
                ", productId=" + productId +
                '}';
    }
}
