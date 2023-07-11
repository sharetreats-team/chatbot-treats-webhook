package com.sharetreats.chatbot.module.controller.dto.retryDtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RetryKeyboard {

    @JsonProperty("Type")
    private String type;

    @JsonProperty("DefaultHeight")
    private boolean defaultHeight;

    @JsonProperty("BgColor")
    private String bgColor;

    @JsonProperty("Buttons")
    private List<RetryButton> Buttons;
}