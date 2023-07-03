package com.sharetreats.chatbot.module.controller.webhook;

import com.sharetreats.chatbot.infra.config.RestTemplateConfig;
import com.sharetreats.chatbot.module.controller.dto.paymentDtos.PaymentFailedMessage;
import com.sharetreats.chatbot.module.controller.dto.paymentDtos.PaymentSuccessMessage;
import com.sharetreats.chatbot.module.entity.GiftHistory;
import com.sharetreats.chatbot.module.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 포인트 결제 요청에 대한 결과를 전송하는 클래스입니다.
 */
@RequiredArgsConstructor
@Component
public class SendPaymentResultMessage extends ResponseEvent {

    static final String VIBER_SEND_MESSAGE_URL = "https://chatapi.viber.com/pa/send_message";

    @Value("${viber.auth.token}")
    private String token;
    
    private final PaymentService paymentService;
    private final RestTemplateConfig restTemplateConfig;


    @Override
    public ResponseEntity<?> execute(String callback) {
        String accountId = getAccountIdToCallback(callback);
        Optional<GiftHistory> result = paymentService.payWithPoints(accountId);
        if(result.isPresent()) return sendSuccessMessage(result.get());
        else return sendFailedMessage(accountId);
    }

    private ResponseEntity<?> sendSuccessMessage(GiftHistory giftHistory) {
        PaymentSuccessMessage response = PaymentSuccessMessage.of(giftHistory);
        HttpHeaders headers = setHttpHeaders();
        HttpEntity<PaymentSuccessMessage> httpEntity = new HttpEntity<>(response, headers);
        return restTemplateConfig.restTemplate().postForEntity(VIBER_SEND_MESSAGE_URL, httpEntity, String.class);
    }

    private ResponseEntity<?> sendFailedMessage(String id) {
        PaymentFailedMessage response = PaymentFailedMessage.of(id);
        HttpHeaders headers = setHttpHeaders();
        HttpEntity<PaymentFailedMessage> httpEntity = new HttpEntity<>(response, headers);
        return restTemplateConfig.restTemplate().postForEntity(VIBER_SEND_MESSAGE_URL, httpEntity, String.class);
    }

    private HttpHeaders setHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Viber-AuthToken", token);
        return headers;
    }

    private static String getAccountIdToCallback(String callback) {
        return new JSONObject(callback).getJSONObject("sender").getString("id");
    }
}
