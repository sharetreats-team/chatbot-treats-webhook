package com.sharetreats.chatbot.module.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ViberTextWithKeyboardMessage {

    @JsonProperty("receiver")
    private String receiver;
    @JsonProperty("min_api_version")
    private int minApiVersion;
    @JsonProperty("tracking_data")
    private String trackingData;
    @JsonProperty("type")
    private String type;
    @JsonProperty("text")
    private String text;
    @JsonProperty("keyboard")
    private ViberSimpleKeyboard keyboard;
}

