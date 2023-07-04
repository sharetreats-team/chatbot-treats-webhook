package com.sharetreats.chatbot.module.service;

import com.sharetreats.chatbot.module.entity.GiftHistory;

import java.util.Optional;

public interface PaymentService {

    /**
     * # 포인트 결제 기능
     * 1) 선물을 위해 사전에 저장했던 선물내역과 구매자의 ID 를 받습니다.
     * 2) 구매정보에서 결제금액과 사용자의 포인트를 비교합니다.
     * ㄴ 결제가 가능하다면,결제로직과 선물내역을 DB에 저장하여 `true` 를 반환합니다.
     * ㄴ 결제가 불가능다면 `false` 를 반환합니다.
     *
     * @param accountId;
     * @return Optional<GiftHistory>
     **/
    Optional<GiftHistory> payWithPoints(String accountId);
}
