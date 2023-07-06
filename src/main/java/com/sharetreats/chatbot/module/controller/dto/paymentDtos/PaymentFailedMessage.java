package com.sharetreats.chatbot.module.controller.dto.paymentDtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sharetreats.chatbot.module.controller.dto.welcomeDtos.WelcomeButton;
import com.sharetreats.chatbot.module.controller.dto.welcomeDtos.WelcomeKeyboard;
import lombok.*;

import java.util.Collections;
import java.util.List;

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

    private WelcomeKeyboard keyboard;

    @JsonProperty("tracking_data")
    private String trackingData;

    private String type;

    private String text;

    public static PaymentFailedMessage of(String id, String avatar) {
        return new PaymentFailedMessage(
                id,
                1,
                new Sender("stchatbot3", avatar),
                WelcomeKeyboard.of("keyboard", false,"#FFFFFF", createButtons()),
                "tracking_data",
                "text",
                "Payment declined due to insufficient points.");
    }

    public static List<WelcomeButton> createButtons() {
        return Collections.singletonList(WelcomeButton.of());
    }
}
