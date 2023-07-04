package com.sharetreats.chatbot.module.controller.dto.paymentDtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sharetreats.chatbot.module.controller.dto.welcomeDtos.WelcomeButton;
import com.sharetreats.chatbot.module.controller.dto.welcomeDtos.WelcomeKeyboard;
import com.sharetreats.chatbot.module.entity.GiftHistory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentSuccessMessage {

    private String receiver;

    private String type;

    @JsonProperty("min_api_version")
    private int minApiVersion;

    private WelcomeKeyboard keyboard;

    @JsonProperty("rich_media")
    private RichMedia richMedia;

    public static PaymentSuccessMessage of(GiftHistory giftHistory) {
        return new PaymentSuccessMessage(
                giftHistory.getSender().getId(),
                "rich_media",
                7,
                WelcomeKeyboard.of("keyboard", false,"#FFFFFF", createButtons()),
                RichMedia.of("rich_media", 6, 7, "#F2F2F2", giftHistory));
    }

    public static List<WelcomeButton> createButtons() {
        return Collections.singletonList(WelcomeButton.of());
    }
}
