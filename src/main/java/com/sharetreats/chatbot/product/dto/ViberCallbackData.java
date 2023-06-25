package com.sharetreats.chatbot.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ViberCallbackData {
    private String event;
    private Long timestamp;
    private Long message_token;
    @JsonProperty("sender")
    private Sender sender;
    @JsonProperty("message")
    private Message message;
    @Getter
    public static class Sender {
        private String id;
        private String name;
        private String avatar;
        private String country;
        private String language;
        private int api_version;

        @Override
        public String toString() {
            return "Sender{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", avatar='" + avatar + '\'' +
                    ", country='" + country + '\'' +
                    ", language='" + language + '\'' +
                    ", api_version=" + api_version +
                    '}';
        }
    }
    @Getter
    public static class Message{
        private String type;
        private String text;
        private String media;
        @JsonProperty("location")
        private Location location;
        private String tracking_data;

        @Override
        public String toString() {
            return "Message{" +
                    "type='" + type + '\'' +
                    ", text='" + text + '\'' +
                    ", media='" + media + '\'' +
                    ", location=" + location +
                    ", tracking_data='" + tracking_data + '\'' +
                    '}';
        }

        @Getter
        public static class Location {
            private double lat;
            private double lon;

            @Override
            public String toString() {
                return "Location{" +
                        "lat=" + lat +
                        ", lon=" + lon +
                        '}';
            }
        }

    }
}
