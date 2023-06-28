package com.sharetreats.chatbot.module.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"Columns", "Rows","BgColor", "ActionType", "ActionBody", "Text", "TextHAlign", "TextVAlign", "TextSize"})
public class WelcomeButton {

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
    private String textVAlign;

    @JsonProperty("TextSize")
    private String textSize;

    public static WelcomeButton of () {
        return new WelcomeButton(6, 1, "#2db9b9", "reply", "show treats",
                "Show Treats", "center", "middle", "large");
    }
}
