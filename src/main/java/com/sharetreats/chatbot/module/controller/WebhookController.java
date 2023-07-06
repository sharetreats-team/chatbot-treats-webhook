package com.sharetreats.chatbot.module.controller;

import com.sharetreats.chatbot.infra.config.TokenConfig;
import com.sharetreats.chatbot.module.controller.webhook.*;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.sharetreats.chatbot.module.controller.WebhookController.EventType.*;
import static com.sharetreats.chatbot.module.controller.WebhookController.InputKeyword.*;

/**
 * Viber 에 챗봇(Chat Bot)과 연결된 서버인 웹후크(역방향 API) 입니다.
 * 이 웹후크는 Viber 에서 전송하는 콜백 및 사용자 메시지를 수신하는 데 사용합니다.
 * 웹훅을 설정하면 계정과의 1:1 대화가 가능해집니다.
 * 계정과의 1:1 대화를 비활성화하려면 웹훅을 제거해야 합니다.
 *
 * @Webhook_URL_Endpoint: "/viber/bot/webhook"
 */
@RestController
@RequiredArgsConstructor
public class WebhookController {

    private final SendWelcomeMessage sendWelcomeMessage;
    private final SendPaymentResultMessage sendPaymentResultMessage;
    private final SendProductsOfBrand sendProductsOfBrand;
    private final SendBrandKeyboardMessage sendBrandKeyboardMessage;
    private final SendPurchaseInfo sendPurchaseInfo;
    private final SendProductDetail sendproductDetail;
    private final ManageSubscription manageSubscription;
    private final TokenConfig tokenConfig;
    private final SendInvalidTokenMessage sendInvalidTokenMessage;

    /**
     * Webhook CallBack Data 를 받는 `MAIN API`
     *
     * @param callback
     * @return ResponseEntity
     */
    @PostMapping("/viber/bot/webhook")
    public ResponseEntity<?> webhook(@RequestBody String callback) {
        String event = getEventValueToCallback(callback);

        if (event.equals(CONVERSATION_STARTED)) {
            //String accountId = getUserId(callback);
            //tokenConfig.generateToken(accountId);
            return sendWelcomeMessage.execute();
        }
        if (event.equals(MESSAGE)) {
            //String accountId = getSenderId(callback);
            //boolean isValidToken = tokenConfig.validateToken(accountId);
            //if (isValidToken)
            return sendResponseByTextInMessage(callback);
            //else return sendInvalidTokenMessage.execute(accountId);
        }
        if (event.equals(UNSUBSCRIBED))
            manageSubscription.unsubscribe(callback);
        if (event.equals(SUBSCRIBED))
            manageSubscription.validateReSubscription(callback);
        return null;
    }

    /**
     * Event 가 메시지일 때, 해당 메시지의 text 에 작성된 `키워드` 따라 기능을 수행
     * 키워드는 현재 9 가지 있습니다.
     *
     * @param callback
     * @return ResponseEntity
     */
    private ResponseEntity<?> sendResponseByTextInMessage(String callback) {
        String text = getTextToMessage(callback);
        String trackingData = getTrackingDataToMessage(callback);
        if (isContains(text, BUY_USE_POINT))
            return sendPaymentResultMessage.execute(callback);
        if (isContains(text, VIEW_PRODUCTS_OF_BRAND))
            return sendProductsOfBrand.execute(callback);
        if (isContains(text, SEND_TREATS) || isContains(text, NO_DISCOUNT) || isTrackingDataValid(trackingData))
          return sendPurchaseInfo.execute(callback);
        if (isContains(text, VIEW_BRANDS)) {
            manageSubscription.validateAccount(callback);
            return sendBrandKeyboardMessage.execute(callback);
        }
        if (isContains(text, VIEW_MORE)) {
            return sendproductDetail.execute(callback);
        }
        throw new IllegalArgumentException("어떠한 이벤트에도 해당하지 않는 문자입니다.");

    }

    private static boolean isContains (String text, String keyword){
        return text.contains(keyword);
    }

    private static String getTextToMessage (String callback){
        return new JSONObject(callback).getJSONObject("message").getString("text");
    }

    private static String getEventValueToCallback (String callback){
        return new JSONObject(callback).getString("event");
    }

    private static String getTrackingDataToMessage (String callback){
        JSONObject messageObject = new JSONObject(callback).getJSONObject("message");
        String trackingData = "";
        if (messageObject.has("tracking_data")) {
            trackingData = messageObject.getString("tracking_data");
        }
        return trackingData;
    }

    private static String getUserId (String callback) {
        JSONObject accountObject = new JSONObject(callback).getJSONObject("user");
        return accountObject.getString("id");
    }

    private static String getSenderId (String callback) {
        JSONObject accountObject = new JSONObject(callback).getJSONObject("sender");
        return accountObject.getString("id");
    }

    private static boolean isTrackingDataValid (String trackingData){
        return trackingData.equals("name") || trackingData.equals("email") || trackingData.equals("message") || trackingData.equals("discount_code");
    }

    static class EventType {
        public static final String CONVERSATION_STARTED = "conversation_started";
        public static final String MESSAGE = "message";
        public static final String SUBSCRIBED = "subscribed";
        public static final String UNSUBSCRIBED = "unsubscribed";
    }

    static class InputKeyword {
        public static final String BUY_USE_POINT = "use point";
        public static final String VIEW_BRANDS = "show treats";
        public static final String VIEW_PRODUCTS_OF_BRAND = "brandId";
        public static final String SEND_TREATS = "send treats";
        public static final String VIEW_MORE = "view more";
        public static final String NO_DISCOUNT = "no discount";
    }
}

