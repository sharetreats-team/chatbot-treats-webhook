package com.sharetreats.chatbot.module.controller.webhook;

import com.sharetreats.chatbot.module.service.TextService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SendPurchaseInfo extends ResponseEvent{
    private final TextService textService;
    @Value("${viber.auth.token}")
    private String viberAuthToken;
    private static final Logger log = LoggerFactory.getLogger(TextService.class);
    @Override
    public ResponseEntity<?> execute(String callback) {
        JSONObject callbackData = new JSONObject(callback);
        String senderId = callbackData.getJSONObject("sender").getString("id");
        JSONObject messageObject = callbackData.getJSONObject("message");
        String trackingData = "";

        if(messageObject.has("tracking_data")) {
            trackingData = messageObject.getString("tracking_data");
        }
        String messageText = messageObject.getString("text");

        log.info(messageObject.toString());
        return textService.sendMessage(senderId, messageText, trackingData, viberAuthToken);
    }
}
