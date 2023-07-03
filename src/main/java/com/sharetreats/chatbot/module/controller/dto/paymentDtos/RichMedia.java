package com.sharetreats.chatbot.module.controller.dto.paymentDtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sharetreats.chatbot.module.entity.GiftHistory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RichMedia {

    @JsonProperty("Type")
    private String type;

    @JsonProperty("ButtonsGroupColumns")
    private int buttonsGroupColumns;

    @JsonProperty("ButtonsGroupRows")
    private int buttonsGroupRows;

    @JsonProperty("BgColor")
    private String bgColor;

    @JsonProperty("Buttons")
    private List<PayResultButtons> buttons;


    public static RichMedia of(String type, int buttonsGroupColumns, int buttonsGroupRows, String bgColor, GiftHistory giftHistory) {
        return new RichMedia(type, buttonsGroupColumns, buttonsGroupRows, bgColor, PayResultButtons.of(giftHistory));
    }
}
