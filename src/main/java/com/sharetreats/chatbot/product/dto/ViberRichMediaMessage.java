package com.sharetreats.chatbot.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
public class ViberRichMediaMessage {
    private String receiver;
    private String type;
    @JsonProperty("min_api_version")
    private int minApiVersion;
    @JsonProperty("rich_media")
    private RichMedia richMedia;

    @Getter
    @Setter
    @Builder
    public static class RichMedia {
        @JsonProperty("Type")
        private String type;
        @JsonProperty("ButtonsGroupColumns")
        private int buttonsGroupColumns;
        @JsonProperty("ButtonsGroupRows")
        private int buttonsGroupRows;
        @JsonProperty("BgColor")
        private String bgColor;
        @JsonProperty("Buttons")
        private List<Map<String, Object>> buttons;
    }
}
