package com.sharetreats.chatbot.module.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ViberSimpleButton {

    @JsonProperty("ActionType")
    private String actionType;
    @JsonProperty("ActionBody")
    private String actionBody;
    @JsonProperty("Text")
    private String text;
    @JsonProperty("TextSize")
    private String textSize;
}