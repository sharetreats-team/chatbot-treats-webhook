package com.sharetreats.chatbot.module.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ViberTextMessage {
    @JsonProperty("receiver")
    private String receiver;

    @JsonProperty("min_api_version")
    private Integer minApiVersion;

    @JsonProperty("sender")
    private Sender sender;

    @JsonProperty("tracking_data")
    private String trackingData;

    @JsonProperty("type")
    private String type;

    @JsonProperty("text")
    private String text;

    @Getter
    @Setter
    @Builder
    public static class Sender {
        @JsonProperty("name")
        private String name;

        @JsonProperty("avatar")
        private String avatar;
    }
}
