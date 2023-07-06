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

    public ResponseEntity<?> sendProductDetailMessage(String receiverId, Long productId, String authToken) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        String sendUrl = "https://chatapi.viber.com/pa/send_message";
        // 텍스트 키보드 메시지 만들고
        // 보내기
        ViberTextWithKeyboardMessage viberProductDetailMessage = makeProductDetailMessage(receiverId, product);
        return sendTextWithKeyboardMessage(authToken, viberProductDetailMessage);
    }

    public ResponseEntity<?> sendPurchaseInfoInputMessage(String receiverId, String messageText, String trackingData, String authToken) {

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
                    .text("Please enter the recipient's email")
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
                    .text("Please enter your message")
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
                    .text("Please enter your discount code!")
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

        return sendTextMessage(authToken, viberTextMessage);
    }

    private ViberTextWithKeyboardMessage makePurchaseInfoMessage(String receiverId, GiftHistoryRedisHash giftHistoryRedisHash) {
        ViberTextWithKeyboardMessage viberMessage = ViberTextWithKeyboardMessage.builder()
                .receiver(receiverId)
                .minApiVersion(1)
                .trackingData("")
                .type("text")
                .text("Check your purchase\n" +
                        "Product:\n" + giftHistoryRedisHash.getProductName() + "\n" +
                        "Payment amount:\n" + giftHistoryRedisHash.getPrice() + " point\n" +
                        "Recipient:\n" + giftHistoryRedisHash.getReceiverName() + "\n" +
                        "Recipient email:\n" + giftHistoryRedisHash.getReceiverEmail() + "\n" +
                        "Message:\n" + giftHistoryRedisHash.getMessage())
                .keyboard(createPurchaseKeyboard(receiverId, giftHistoryRedisHash.getProductId()))
                .build();
        return viberMessage;
    }

    private ViberTextWithKeyboardMessage makeProductDetailMessage(String receiverId, Product product) {
        String text = String.format(
                "브랜드명: %s\n상품명: %s\n상품 가격: %d\n할인 가격: %d\n상품 설명\n%s",
                product.getBrandName(),
                product.getName(),
                product.getPrice(),
                product.getDiscountPrice(),
                product.getDescription()
        );
        ViberTextWithKeyboardMessage viberMessage = ViberTextWithKeyboardMessage.builder()
                .receiver(receiverId)
                .minApiVersion(1)
                .trackingData("")
                .type("text")
                .text(text)
                .keyboard(createProductDetailKeyboard(product))
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
                .columns(6)
                .rows(1)
                .bgColor("#29A7D9")
                .actionType("reply")
                .actionBody("use point")
                .text("Confirm")
                .textSize("regular")
                .build();

        ViberSimpleButton wrongInfoButton = ViberSimpleButton.builder()
                .columns(6)
                .rows(1)
                .bgColor("#29A7D9")
                .actionType("reply")
                .actionBody("send treats " + productId)
                .text("Type it again")
                .textSize("regular")
                .build();

        ViberSimpleKeyboard keyboard = ViberSimpleKeyboard.builder()
                .type("keyboard")
                .defaultHeight(false)
                .bgColor("#FFFFFF")
                .buttons(Arrays.asList(nextButton, wrongInfoButton))
                .build();

        return keyboard;
    }
    private ViberSimpleKeyboard createNoDiscountKeyboard() {
        ViberSimpleButton nextButton = ViberSimpleButton.builder()
                .columns(6)
                .rows(1)
                .bgColor("#29A7D9")
                .actionType("reply")
                .actionBody("no discount")
                .text("No Code")
                .textSize("regular")
                .build();

        ViberSimpleKeyboard keyboard = ViberSimpleKeyboard.builder()
                .type("keyboard")
                .defaultHeight(false)
                .bgColor("#FFFFFF")
                .buttons(singletonList(nextButton))
                .build();

        return keyboard;
    }

    private ViberSimpleKeyboard createProductDetailKeyboard(Product product) {
        ViberSimpleButton sendTreatsButton = ViberSimpleButton.builder()
                .columns(6)
                .rows(1)
                .bgColor("#29A7D9")
                .actionType("reply")
                .actionBody("send treats " + product.getId())
                .text("BUY")
                .textSize("regular")
                .build();

        ViberSimpleButton discountPlaceButton = ViberSimpleButton.builder()
                .columns(6)
                .rows(1)
                .bgColor("#29A7D9")
                .actionType("open-url")
                .actionBody(product.getDiscountShop())
                .text("GO DISCOUNT PLACE")
                .textSize("regular")
                .build();

        ViberSimpleKeyboard keyboard = ViberSimpleKeyboard.builder()
                .type("keyboard")
                .defaultHeight(false)
                .bgColor("#FFFFFF")
                .buttons(Arrays.asList(sendTreatsButton, discountPlaceButton))
                .build();

        return keyboard;
    }
}

