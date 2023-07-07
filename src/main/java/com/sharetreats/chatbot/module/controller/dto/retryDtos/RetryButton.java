package com.sharetreats.chatbot.module.controller.dto.retryDtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@JsonPropertyOrder({"Columns", "Rows","BgColor", "ActionType", "ActionBody", "Text", "TextHAlign", "TextVAlign", "TextSize"})
@AllArgsConstructor
@Getter
public class RetryButton {
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

    @JsonProperty("TextHAlign")
    private String textHAlign;

    @JsonProperty("TextVAlign")
    private String TextVAlign;

    @JsonProperty("TextSize")
    private String textSize;

}