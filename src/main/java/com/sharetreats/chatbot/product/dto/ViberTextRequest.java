package com.sharetreats.chatbot.product.dto;

import java.util.Map;
import java.util.Objects;

public class ViberTextRequest {
    private String receiver;
    private int min_api_version;
    private Sender sender;
    private String tracking_data;
    private String type;
    private String text;

    public String getReceiver() {
        return receiver;
    }

    public int getMin_api_version() {
        return min_api_version;
    }

    public Sender getSender() {
        return sender;
    }

    public String getTracking_data() {
        return tracking_data;
    }

    public String getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public ViberTextRequest() {
    }


    public static class Sender {
        private String name;
        private String avatar;

        public String getName() {
            return name;
        }

        public String getAvatar() {
            return avatar;
        }
    }
}
