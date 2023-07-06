package com.sharetreats.chatbot.module.controller.dto.categoryDtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryKeyboard {
    @JsonProperty("Type")
    private String type;

    @JsonProperty("DefaultHeight")
    private boolean defaultHeight;

    @JsonProperty("BgColor")
    private String bgColor;

    @JsonProperty("Buttons")
    private List<CategoryButtons> buttons;

    public static CategoryKeyboard of(List<CategoryButtons> buttonsList) {
        return new CategoryKeyboard("keyboard", false,"#FFFFFF", buttonsList);
    }
}
