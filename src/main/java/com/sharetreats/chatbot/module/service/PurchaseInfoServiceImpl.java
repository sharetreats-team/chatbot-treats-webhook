package com.sharetreats.chatbot.module.service;

import com.sharetreats.chatbot.module.dto.GiftHistoryRedisHash;
import com.sharetreats.chatbot.module.dto.ViberTextMessage;
import com.sharetreats.chatbot.module.dto.ViberTextWithKeyboardMessage;
import com.sharetreats.chatbot.module.entity.DiscountCode;
import com.sharetreats.chatbot.module.entity.Product;
import com.sharetreats.chatbot.module.repository.ProductRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PurchaseInfoServiceImpl implements PurchaseInfoService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ProductRepository productRepository;
    private final KeyboardService keyboardService;
    private final DiscountService discountService;

    public PurchaseInfoServiceImpl(RedisTemplate<String, Object> redisTemplate, ProductRepository productRepository, KeyboardService keyboardService, DiscountService discountService) {
        this.redisTemplate = redisTemplate;
        this.productRepository = productRepository;
        this.keyboardService = keyboardService;
        this.discountService = discountService;
    }

    @Override
    public ViberTextWithKeyboardMessage makeMessage(String receiverId, GiftHistoryRedisHash giftHistoryRedisHash) {
        return ViberTextWithKeyboardMessage.builder()
                .receiver(receiverId)
                .minApiVersion(1)
                .trackingData("")
                .type("text")
                .text("*Check your purchase*\n\n" +
                        "*Product:*\n" + giftHistoryRedisHash.getProductName() + "\n\n" +
                        "*Payment amount:*\n" + giftHistoryRedisHash.getPrice() + " *point*\n\n" +
                        "*Recipient:*\n" + giftHistoryRedisHash.getReceiverName() + "\n\n" +
                        "*Recipient email:*\n" + giftHistoryRedisHash.getReceiverEmail() + "\n\n" +
                        "*Message:*\n" + giftHistoryRedisHash.getMessage())
                .keyboard(keyboardService.createPurchaseKeyboard(receiverId, giftHistoryRedisHash.getProductId()))
                .build();
    }

    @Override
    public ResponseEntity<?> sendMessage(String receiverId, String messageText, String trackingData, String authToken) {
        ViberTextMessage viberTextMessage = null;

        if (trackingData.equals("name")) {
            String recipantName = messageText;

            GiftHistoryRedisHash giftHistoryRedisHash = getGiftHistory(receiverId);
            giftHistoryRedisHash.setReceiverName(recipantName);

            saveGiftHistory(receiverId, giftHistoryRedisHash);

            viberTextMessage = ViberTextMessage.builder()
                    .receiver(receiverId)
                    .minApiVersion(1)
                    .trackingData("email")
                    .type("text")
                    .text("Please enter the recipient's email")
                    .build();
        } else if (trackingData.equals("email")) {
            String gifteeEmail = messageText;
            GiftHistoryRedisHash giftHistoryRedisHash = getGiftHistory(receiverId);
            giftHistoryRedisHash.setReceiverEmail(gifteeEmail);

            saveGiftHistory(receiverId, giftHistoryRedisHash);

            viberTextMessage = ViberTextMessage.builder()
                    .receiver(receiverId)
                    .minApiVersion(1)
                    .trackingData("message")
                    .type("text")
                    .text("Please enter your message")
                    .build();
        } else if (trackingData.equals("message")) {
            String gifteeMessage = messageText;
            GiftHistoryRedisHash giftHistoryRedisHash = getGiftHistory(receiverId);

            giftHistoryRedisHash.setMessage(gifteeMessage);

            saveGiftHistory(receiverId, giftHistoryRedisHash);

            ViberTextWithKeyboardMessage viberMessage = ViberTextWithKeyboardMessage.builder()
                    .receiver(receiverId)
                    .minApiVersion(1)
                    .trackingData("discount_code")
                    .type("text")
                    .text("Please enter your discount code!")
                    .keyboard(keyboardService.createNoDiscountKeyboard())
                    .build();
            return getSendTextWithKeyboardResponse(authToken, viberMessage);

        } else if (messageText.equals("no discount")) {
            GiftHistoryRedisHash giftHistoryRedisHash = getGiftHistory(receiverId);

            ViberTextWithKeyboardMessage viberMessage = makeMessage(receiverId, giftHistoryRedisHash);
            return getSendTextWithKeyboardResponse(authToken, viberMessage);

        } else if (trackingData.equals("discount_code")) {
            String discountCode = messageText;
            GiftHistoryRedisHash giftHistoryRedisHash = getGiftHistory(receiverId);
            DiscountCode validDiscountCode = discountService.validateDiscountCode(discountCode);

            if (validDiscountCode != null) {
                int finalDiscountPrice = discountService.calculateDiscount(validDiscountCode, giftHistoryRedisHash.getPrice());
                giftHistoryRedisHash.setPrice(finalDiscountPrice);
                saveGiftHistory(receiverId, giftHistoryRedisHash);

                ViberTextWithKeyboardMessage viberMessage = makeMessage(receiverId, giftHistoryRedisHash);
                return getSendTextWithKeyboardResponse(authToken, viberMessage);
            }

            viberTextMessage = ViberTextMessage.builder()
                    .receiver(receiverId)
                    .minApiVersion(1)
                    .trackingData("discount_code")
                    .type("text")
                    .text("Invalid discount code.\n Please enter it again.")
                    .build();

        } else if (trackingData.equals("")) {
            Long productId = findProductId(messageText);
            if (productId != null) {
                GiftHistoryRedisHash giftHistoryRedisHash = new GiftHistoryRedisHash();
                giftHistoryRedisHash.setProductId(productId);
                Product product = productRepository.findById(productId).orElse(null);
                if (product != null) {
                    giftHistoryRedisHash.setPrice(product.getDiscountPrice());
                    giftHistoryRedisHash.setProductName(product.getName());
                }
                saveGiftHistory(receiverId, giftHistoryRedisHash);

                viberTextMessage = ViberTextMessage.builder()
                        .receiver(receiverId)
                        .minApiVersion(1)
                        .trackingData("name")
                        .type("text")
                        .text("Please enter the recipient's name")
                        .build();
            }
        }

        if (viberTextMessage == null) {
            throw new IllegalArgumentException("Invalid tracking data: " + trackingData);
        }

        return getSendTextResponse(authToken, viberTextMessage);
    }

    @Override
    public ResponseEntity<ViberTextMessage> getSendTextResponse(String authToken, ViberTextMessage viberTextMessage) {
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

    @Override
    public ResponseEntity<ViberTextWithKeyboardMessage> getSendTextWithKeyboardResponse(String authToken, ViberTextWithKeyboardMessage viberTextWithKeyboardMessage) {
        String sendUrl = "https://chatapi.viber.com/pa/send_message";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Viber-Auth-Token", authToken);

        HttpEntity<ViberTextWithKeyboardMessage> httpEntity = new HttpEntity<>(viberTextWithKeyboardMessage, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(sendUrl, httpEntity, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok(viberTextWithKeyboardMessage);
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
