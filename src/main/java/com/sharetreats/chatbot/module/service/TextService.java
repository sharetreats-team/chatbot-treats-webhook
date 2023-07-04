package com.sharetreats.chatbot.module.service;

import com.sharetreats.chatbot.module.dto.*;
import com.sharetreats.chatbot.module.entity.DiscountCode;
import com.sharetreats.chatbot.module.entity.Product;
import com.sharetreats.chatbot.module.option.DiscountType;
import com.sharetreats.chatbot.module.repository.DiscountCodeRepository;
import com.sharetreats.chatbot.module.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Collections.*;

@Service
public class TextService {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final Logger log = LoggerFactory.getLogger(TextService.class);
    private final ProductRepository productRepository;
    private final DiscountCodeRepository discountCodeRepository;

    public TextService(RedisTemplate<String, Object> redisTemplate,
                       ProductRepository productRepository,
                       DiscountCodeRepository discountCodeRepository) {
        this.redisTemplate = redisTemplate;
        this.productRepository = productRepository;
        this.discountCodeRepository = discountCodeRepository;
    }

    public ResponseEntity<?> sendMessage(String receiverId, String messageText, String trackingData, String authToken) {

        ViberTextMessage viberTextMessage = null;

        if (trackingData.equals("name")) {
            String gifteeName = messageText;

            GiftHistoryRedisHash giftHistoryRedisHash = getGiftHistory(receiverId);
            giftHistoryRedisHash.setReceiverName(gifteeName);
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

            ViberTextWithKeyboardMessage viberMessage = ViberTextWithKeyboardMessage.builder()
                    .receiver(receiverId)
                    .minApiVersion(1)
                    .trackingData("discount_code")
                    .type("text")
                    .text("할인 코드를 입력해 주세요!")
                    .keyboard(createNoDiscountKeyboard())
                    .build();
            return sendTextWithKeyboardMessage(authToken, viberMessage);

        } else if (messageText.equals("no discount")) {
            GiftHistoryRedisHash giftHistoryRedisHash = getGiftHistory(receiverId);
            log.info(giftHistoryRedisHash.toString());

            ViberTextWithKeyboardMessage viberMessage = makePurchaseInfoMessage(receiverId, giftHistoryRedisHash);
            return sendTextWithKeyboardMessage(authToken, viberMessage);

        } else if (trackingData.equals("discount_code")) {
            String discountCode = messageText;
            GiftHistoryRedisHash giftHistoryRedisHash = getGiftHistory(receiverId);
            log.info(giftHistoryRedisHash.toString());
            DiscountCode validDiscountCode = validateDiscountCode(discountCode);

            if (validDiscountCode != null) {
                int finalDiscountPrice = calculateDiscount(validDiscountCode, giftHistoryRedisHash.getPrice());
                giftHistoryRedisHash.setPrice(finalDiscountPrice);
                saveGiftHistory(receiverId, giftHistoryRedisHash);
                log.info(giftHistoryRedisHash.toString());

                ViberTextWithKeyboardMessage viberMessage = makePurchaseInfoMessage(receiverId, giftHistoryRedisHash);
                return sendTextWithKeyboardMessage(authToken, viberMessage);
            }

            viberTextMessage = ViberTextMessage.builder()
                    .receiver(receiverId)
                    .minApiVersion(1)
                    .trackingData("discount_code")
                    .type("text")
                    .text("유효하지 않은 할인 코드입니다. 다시 입력해주세요.")
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
                        .text("이름을 입력해주세요")
                        .build();
            }
        }

        if (viberTextMessage == null) {
            throw new IllegalArgumentException("Invalid tracking data: " + trackingData);
        }

        return sendTextMessage(authToken, viberTextMessage);
    }

    private ViberTextWithKeyboardMessage makePurchaseInfoMessage(String receiverId, GiftHistoryRedisHash giftHistoryRedisHash) {
        ViberTextWithKeyboardMessage viberMessage = ViberTextWithKeyboardMessage.builder()
                .receiver(receiverId)
                .minApiVersion(1)
                .trackingData("")
                .type("text")
                .text("선물 내용 확인\n" +
                        "구입하시는 상품:\n" + giftHistoryRedisHash.getProductName() + "\n" +
                        "결제 금액:\n" + giftHistoryRedisHash.getPrice() + " point\n" +
                        "선물받는 사람:\n" + giftHistoryRedisHash.getReceiverName() + "\n" +
                        "받는 사람 이메일:\n" + giftHistoryRedisHash.getReceiverEmail() + "\n" +
                        "보낼 메시지:\n" + giftHistoryRedisHash.getMessage() + "\n\n" +
                        "위 내용이 맞다면 Point Payment, 다시 입력하시려면 Wrong Information 버튼을 클릭해주세요")
                .keyboard(createPurchaseKeyboard(receiverId, giftHistoryRedisHash.getProductId()))
                .build();
        return viberMessage;
    }

    private ResponseEntity<ViberTextMessage> sendTextMessage(String authToken, ViberTextMessage viberTextMessage) {
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

    private ResponseEntity<ViberTextWithKeyboardMessage> sendTextWithKeyboardMessage(String authToken, ViberTextWithKeyboardMessage viberTextWithKeyboardMessage) {
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

    private DiscountCode validateDiscountCode(String discountCode) {
        // 할인 코드 검증 로직을 구현하여 데이터베이스에서 할인 코드를 조회하고 유효성을 검사합니다.
        // 유효한 할인 코드인 경우 해당 할인 코드 엔티티를 반환합니다.
        // 유효하지 않은 경우 null을 반환합니다.
        return discountCodeRepository.findByCode(discountCode);
    }

    private int calculateDiscount(DiscountCode discountCode, int originalPrice) {

        // 할인 종류가 "고정 금액 할인"인 경우
        if (discountCode.getType() == DiscountType.FIX_DISCOUNT) {
            return originalPrice - discountCode.getDiscountPrice();
        }

        // 할인 종류가 "비율 금액 할인"인 경우
        if (discountCode.getType() == DiscountType.RATE_DISCOUNT) {
            double discountPercentage = discountCode.getDiscountPrice() / 100.0;
            double discountAmount = originalPrice * discountPercentage;
            return (int) Math.round(originalPrice - discountAmount);
        }

        return originalPrice; // 할인이 적용되지 않은 경우 원래 가격을 반환합니다.
    }
    private ViberSimpleKeyboard createPurchaseKeyboard(String receiverId, Long productId) {
        ViberSimpleButton nextButton = ViberSimpleButton.builder()
                .actionType("reply")
                .actionBody("use point")
                .text("Point Payment")
                .textSize("regular")
                .build();

        ViberSimpleButton wrongInfoButton = ViberSimpleButton.builder()
                .actionType("reply")
                .actionBody("send treats " + productId)
                .text("Wrong Information")
                .textSize("regular")
                .build();

        ViberSimpleKeyboard keyboard = ViberSimpleKeyboard.builder()
                .type("keyboard")
                .defaultHeight(false)
                .buttons(Arrays.asList(nextButton, wrongInfoButton))
                .build();

        return keyboard;
    }
    private ViberSimpleKeyboard createNoDiscountKeyboard() {
        ViberSimpleButton nextButton = ViberSimpleButton.builder()
                .actionType("reply")
                .actionBody("no discount")
                .text("No Code")
                .textSize("regular")
                .build();

        ViberSimpleKeyboard keyboard = ViberSimpleKeyboard.builder()
                .type("keyboard")
                .defaultHeight(false)
                .buttons(singletonList(nextButton))
                .build();

        return keyboard;
    }
}

