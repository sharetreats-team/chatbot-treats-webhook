package com.sharetreats.chatbot.module.webhook;

import org.springframework.web.bind.annotation.RestController;

/**
 * Viber 에 챗봇(Chat Bot)과 연결된 서버인 웹후크(역방향 API) 입니다.
 * 이 웹후크는 Viber 에서 전송하는 콜백 및 사용자 메시지를 수신하는 데 사용합니다.
 * 웹훅을 설정하면 계정과의 1:1 대화가 가능해집니다.
 * 계정과의 1:1 대화를 비활성화하려면 웹훅을 제거해야 합니다.
 *
 * @Webhook_URL_Endpoint: "/viber/bot/webhook"
 */
@RestController
public class WebhookController {

}
