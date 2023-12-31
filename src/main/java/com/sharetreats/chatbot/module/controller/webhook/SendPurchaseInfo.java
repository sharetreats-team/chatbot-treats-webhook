package com.sharetreats.chatbot.module.controller.webhook;

import com.sharetreats.chatbot.module.service.PurchaseInfoService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SendPurchaseInfo extends ResponseEvent{
    private final PurchaseInfoService purchaseInfoService;
    @Value("${viber.auth.token}")
    private String viberAuthToken;
    @Override
    public ResponseEntity<?> execute(String callback) {
        JSONObject callbackData = new JSONObject(callback);
        String senderId = callbackData.getJSONObject("sender").getString("id");
        JSONObject messageObject = callbackData.getJSONObject("message");
        String messageText = messageObject.getString("text");
        String trackingData = "";

        if(messageObject.has("tracking_data")) {
            trackingData = messageObject.getString("tracking_data");
        }

        return purchaseInfoService.sendMessage(senderId, messageText, trackingData, viberAuthToken);
    }
}
