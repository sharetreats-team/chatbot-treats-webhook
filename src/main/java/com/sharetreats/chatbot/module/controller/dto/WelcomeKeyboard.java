package com.sharetreats.chatbot.module.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WelcomeKeyboard {

    @JsonProperty("Type")
    private String type;

    @JsonProperty("DefaultHeight")
    private boolean defaultHeight;

    @JsonProperty("BgColor")
    private String bgColor;

    @JsonProperty("Buttons")
    private List<WelcomeButton> buttons;

    public static WelcomeKeyboard of(String type, boolean defaultHeight, String bgColor, List<WelcomeButton> buttonsList) {
        return new WelcomeKeyboard(type, defaultHeight, bgColor,
                buttonsList.stream()
                        .map(b -> WelcomeButton.of())
                        .collect(Collectors.toList()));
    }
}
