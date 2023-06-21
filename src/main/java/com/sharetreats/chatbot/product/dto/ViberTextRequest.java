package com.sharetreats.chatbot.product.dto;

import lombok.Getter;

@Getter
public class ViberTextRequest {
    private String receiver;
    private int min_api_version;
    private Sender sender;
    private String tracking_data;
    private String type;
    private String text;

    @Getter
    public static class Sender {
        private String name;
        private String avatar;
    }
}
