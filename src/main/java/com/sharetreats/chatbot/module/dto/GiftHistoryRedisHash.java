package com.sharetreats.chatbot.module.dto;

import com.sharetreats.chatbot.module.entity.Account;
import com.sharetreats.chatbot.module.entity.GiftHistory;
import com.sharetreats.chatbot.module.entity.Product;
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

    private String receiverName;
    private String receiverEmail;
    private String message;
    private Integer price;
    private Long productId;

    @Override
    public String toString() {
        return "GiftHistoryRedisHash{" +
                ", receiverName='" + receiverName + '\'' +
                ", email='" + receiverEmail + '\'' +
                ", message='" + message + '\'' +
                ", point=" + price +
                ", productId=" + productId +
                '}';
    }

    public GiftHistory saveGiftHistoryWithSender(Account sender, Product product) {
        return GiftHistory.of(
                this.receiverName,
                this.receiverEmail,
                this.message,
                this.price,
                sender,
                product);
    }
}
