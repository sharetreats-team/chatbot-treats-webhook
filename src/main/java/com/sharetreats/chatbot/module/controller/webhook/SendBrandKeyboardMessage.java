package com.sharetreats.chatbot.module.controller.webhook;

import com.sharetreats.chatbot.infra.config.RestTemplateConfig;
import com.sharetreats.chatbot.module.controller.dto.brandDtos.BrandKeyboard;
import com.sharetreats.chatbot.module.controller.dto.brandDtos.BrandResponse;
import com.sharetreats.chatbot.module.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SendBrandKeyboardMessage {
    private final BrandService brandService;
    private final RestTemplateConfig restTemplateConfig;
    static final String VIBER_SEND_MESSAGE_URL = "https://chatapi.viber.com/pa/send_message";
    @Value("${viber.auth.token}")
    private String token;

    public ResponseEntity<?> execute(String callback, String text) {

        String strCategoryId = text.substring(11);
        Long categoryId = Long.parseLong(strCategoryId);

        BrandResponse response = findBrandListForReceiverId(getIdFromSender(callback), categoryId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Viber-Auth-Token", token);

        HttpEntity<BrandResponse> httpEntity = new HttpEntity<>(response, headers);
        ResponseEntity<String> restResponse = restTemplateConfig.restTemplate().postForEntity(VIBER_SEND_MESSAGE_URL, httpEntity, String.class);
        return restResponse;
    }

    public BrandResponse findBrandListForReceiverId(String id, Long categoryId) {
        BrandKeyboard keyboard = BrandKeyboard.of(brandService.createBrandButtons(categoryId));
        return BrandResponse.of(id, keyboard);
    }

    private static String getIdFromSender(String callback) {
        return new JSONObject(callback).getJSONObject("sender").getString("id");
    }

}
