package com.sharetreats.chatbot.infra.mail;

/**
 * Email 전송 서비스
 * 실제 배포 환경에서는 `Gmail-SMTP` 를 통해 `HTML Email` 을 전송합니다.
 * 테스트 환경에서는 `콘솔창에 로그`를 찍는 방식입니다.
 * 이와 같이 전략에 따라 방법이 드리기 때문에 전송 기능을 추상화했습니다.
 */
public interface EmailService {

    void send(EmailMessage emailMessage);
}
