package com.sharetreats.chatbot.module.controller.webhook;

import com.sharetreats.chatbot.module.service.ProductDetailService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
@RequiredArgsConstructor
@Component
public class SendProductDetail extends ResponseEvent {
    private final ProductDetailService productDetailService;
    @Value("${viber.auth.token}")
    private String viberAuthToken;
    @Override
    public ResponseEntity<?> execute(String callback) {
        JSONObject callbackData = new JSONObject(callback);
        String senderId = callbackData.getJSONObject("sender").getString("id");
        Long productId = findProductId(callback);
        return productDetailService.sendMessage(senderId, productId, viberAuthToken);
    }

    private Long findProductId(String messageText) {
        Matcher matcher = Pattern.compile("view more (\\d+)").matcher(messageText);

        if (matcher.find()) {
            return Long.parseLong(matcher.group(1));
        } else {
            throw new IllegalArgumentException("No product ID found in the message: " + messageText);
        }
    }
}
