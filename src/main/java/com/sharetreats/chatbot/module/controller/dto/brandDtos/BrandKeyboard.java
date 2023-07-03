package com.sharetreats.chatbot.module.controller.dto.brandDtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BrandKeyboard {
    @JsonProperty("Type")
    private String type;

    @JsonProperty("DefaultHeight")
    private boolean defaultHeight;

    @JsonProperty("BgColor")
    private String bgColor;

    @JsonProperty("Buttons")
    private List<BrandButtons> buttons;

    public static BrandKeyboard of(List<BrandButtons> buttonsList) {
        return new BrandKeyboard("keyboard", false,"#FFFFFF", buttonsList);
    }
}
