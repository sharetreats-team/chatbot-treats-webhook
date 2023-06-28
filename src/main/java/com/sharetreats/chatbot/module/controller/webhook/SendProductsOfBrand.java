package com.sharetreats.chatbot.module.controller.webhook;

import com.sharetreats.chatbot.module.service.RichMediaService;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SendProductsOfBrand {

    private final RichMediaService richMediaService;

    public SendProductsOfBrand(RichMediaService richMediaService) {
        this.richMediaService = richMediaService;
    }

    public ResponseEntity<?> execute(String callback) {
        JSONObject callbackData = new JSONObject(callback);
        String senderId = callbackData.getJSONObject("sender").getString("id");
        String messageText = callbackData.getJSONObject("message").getString("text");
        String viber_auth_token = "512b538aa327e284-b4accf19ae3e79ad-cffb22f50a17ca97";
        Long brandId = findBrandId(messageText);

        return richMediaService.sendProductsByBrand(senderId, brandId, viber_auth_token);
    }

    private Long findBrandId(String messageText) {
        Matcher matcher = Pattern.compile("brandName (\\\\d+)").matcher(messageText);

        if (matcher.find()) {
            return Long.parseLong(matcher.group(1));
        } else {
            throw new IllegalArgumentException("No brand ID found in the message: " + messageText);
        }
    }
}
