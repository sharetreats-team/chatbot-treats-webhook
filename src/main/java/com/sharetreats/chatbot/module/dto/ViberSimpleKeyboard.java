package com.sharetreats.chatbot.module.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ViberSimpleKeyboard {
    @JsonProperty("Type")
    private String type;
    @JsonProperty("DefaultHeight")
    private boolean defaultHeight;
    @JsonProperty("Buttons")
    private List<ViberSimpleButton> buttons;
}

