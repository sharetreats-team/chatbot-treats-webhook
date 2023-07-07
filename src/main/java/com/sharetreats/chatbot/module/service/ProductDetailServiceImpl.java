package com.sharetreats.chatbot.module.service;

import com.sharetreats.chatbot.module.dto.ViberTextWithKeyboardMessage;
import com.sharetreats.chatbot.module.entity.Product;
import com.sharetreats.chatbot.module.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class ProductDetailServiceImpl implements ProductDetailService {
    private final ProductRepository productRepository;
    private final KeyboardService keyboardService;

    @Override
    public ViberTextWithKeyboardMessage makeMessage(String receiverId, Product product) {
        String text = String.format(
                "*BRAND*\n %s\n*PRODUCT\n* %s\n*PRICE*\n %d point\n*DISCOUNT PRICE\n* %d point\n\n*DESCRIPTION*\n%s",
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
                .keyboard(keyboardService.createProductDetailKeyboard(product))
                .build();
        return viberMessage;
    }

    @Override
    public ResponseEntity<?> sendMessage(String receiverId, Long productId, String authToken) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        ViberTextWithKeyboardMessage viberProductDetailMessage = makeMessage(receiverId, product);
        return getSendTextWithKeyboardResponse(authToken, viberProductDetailMessage);
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
}
