package com.sharetreats.chatbot.module.service;

import com.sharetreats.chatbot.module.dto.GiftHistoryRedisHash;
import com.sharetreats.chatbot.module.dto.ViberTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TextService {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final Logger log = LoggerFactory.getLogger(TextService.class);

    public TextService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public ResponseEntity<ViberTextMessage> sendMessage(String receiverId, String messageText, String trackingData, String authToken) {

        ViberTextMessage viberTextMessage = null;

        if (trackingData.equals("name")) {
            String gifteeName = messageText;

            GiftHistoryRedisHash giftHistoryRedisHash = getGiftHistory(receiverId);
            giftHistoryRedisHash.setReceiverName(gifteeName);
            giftHistoryRedisHash.setSenderId(receiverId);
            log.info(giftHistoryRedisHash.toString());

            saveGiftHistory(receiverId, giftHistoryRedisHash);

            viberTextMessage = ViberTextMessage.builder()
                    .receiver(receiverId)
                    .minApiVersion(1)
                    .trackingData("email")
                    .type("text")
                    .text("이메일을 입력해주세요")
                    .build();
        } else if (trackingData.equals("email")) {
            String gifteeEmail = messageText;

            GiftHistoryRedisHash giftHistoryRedisHash = getGiftHistory(receiverId);
            giftHistoryRedisHash.setReceiverEmail(gifteeEmail);
            log.info(giftHistoryRedisHash.toString());

            saveGiftHistory(receiverId, giftHistoryRedisHash);

            viberTextMessage = ViberTextMessage.builder()
                    .receiver(receiverId)
                    .minApiVersion(1)
                    .trackingData("message")
                    .type("text")
                    .text("메시지를 입력해주세요")
                    .build();
        } else if (trackingData.equals("message")) {
            String gifteeMessage = messageText;

            GiftHistoryRedisHash giftHistoryRedisHash = getGiftHistory(receiverId);
            giftHistoryRedisHash.setMessage(gifteeMessage);
            log.info(giftHistoryRedisHash.toString());

            saveGiftHistory(receiverId, giftHistoryRedisHash);

            viberTextMessage = ViberTextMessage.builder()
                    .receiver(receiverId)
                    .minApiVersion(1)
                    .trackingData("discount_code")
                    .type("text")
                    .text("할인 코드를 입력해주세요")
                    .build();
        } else if (trackingData.equals("discount_code")) {

            GiftHistoryRedisHash giftHistoryRedisHash = getGiftHistory(receiverId);
            log.info(giftHistoryRedisHash.toString());
            saveGiftHistory(receiverId, giftHistoryRedisHash);

            viberTextMessage = ViberTextMessage.builder()
                    .receiver(receiverId)
                    .minApiVersion(1)
                    .trackingData("completed")
                    .type("text")
                    .text("입력 완료")
                    .build();
        } else if (trackingData.equals("")) {
            Long productId = findProductId(messageText);
            if (productId != null) {
                GiftHistoryRedisHash giftHistoryRedisHash = new GiftHistoryRedisHash();
                giftHistoryRedisHash.setProductId(productId);
                saveGiftHistory(receiverId, giftHistoryRedisHash);

                viberTextMessage = ViberTextMessage.builder()
                        .receiver(receiverId)
                        .minApiVersion(1)
                        .trackingData("name")
                        .type("text")
                        .text("이름을 입력해주세요")
                        .build();
            }
        }

        if (viberTextMessage == null) {
            throw new IllegalArgumentException("Invalid tracking data: " + trackingData);
        }

        String sendUrl = "https://chatapi.viber.com/pa/send_message";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Viber-Auth-Token", authToken);

        HttpEntity<ViberTextMessage> httpEntity = new HttpEntity<>(viberTextMessage, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(sendUrl, httpEntity, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok(viberTextMessage);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private void saveGiftHistory(String key, GiftHistoryRedisHash giftHistoryRedisHash) {
        redisTemplate.opsForValue().set(key, giftHistoryRedisHash);
    }

    private GiftHistoryRedisHash getGiftHistory(String key) {
        return (GiftHistoryRedisHash) redisTemplate.opsForValue().get(key);
    }

    private Long findProductId(String messageText) {
        Matcher matcher = Pattern.compile("send treats (\\d+)").matcher(messageText);

        if (matcher.find()) {
            return Long.parseLong(matcher.group(1));
        } else {
            return null;
        }
    }
}
