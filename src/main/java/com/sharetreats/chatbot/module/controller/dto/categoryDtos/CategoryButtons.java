package com.sharetreats.chatbot.module.controller.dto.categoryDtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"Columns", "Rows", "Text", "TextSize",
        "TextHAlign", "TextVAlign", "ActionType", "ActionBody", "BgColor", "BgMediaScaleType"})
public class CategoryButtons {

    @JsonProperty("Columns")
    private int columns;

    @JsonProperty("Rows")
    private int rows;

    @JsonProperty("Text")
    private String text;

    @JsonProperty("TextSize")
    private String textSize;

    @JsonProperty("TextHAlign")
    private String textHAlign;

    @JsonProperty("TextVAlign")
    private String textVAlign;

    @JsonProperty("ActionType")
    private String actionType;

    @JsonProperty("ActionBody")
    private String actionBody;

    @JsonProperty("BgColor")
    private String bgColor;

    @JsonProperty("BgMediaScaleType")
    private String bgMediaScaleType;

}
