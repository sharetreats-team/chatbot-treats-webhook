package com.sharetreats.chatbot.module.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ViberSimpleButton {
    @JsonProperty("Columns")
    private int columns;
    @JsonProperty("Rows")
    private int rows;
    @JsonProperty("BgColor")
    private String bgColor;
    @JsonProperty("ActionType")
    private String actionType;
    @JsonProperty("ActionBody")
    private String actionBody;
    @JsonProperty("Text")
    private String text;
    @JsonProperty("TextVAlign")
    private String textVAlign;
    @JsonProperty("TextHAlign")
    private String textHAlign;
    @JsonProperty("TextSize")
    private String textSize;



}