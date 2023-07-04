package com.sharetreats.chatbot.module.controller.dto.paymentDtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * TYPE: TextMessage
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentFailedMessage {

    private String receiver;

    @JsonProperty("min_api_version")
    private int minApiVersion;

    private Sender sender;

    @JsonProperty("tracking_data")
    private String trackingData;

    private String type;

    private String text;

    public static PaymentFailedMessage of(String id, String avatar) {
        return new PaymentFailedMessage(
                id,
                1,
                new Sender("stchatbot3", avatar),
                "tracking_data",
                "text",
                "[포인트 부족] 결제에 실패했습니다.");
    }
}
