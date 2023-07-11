package com.sharetreats.chatbot.module.repository;

import com.sharetreats.chatbot.module.entity.GiftHistory;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GiftHistoryRepository extends JpaRepository<GiftHistory, Long> {

    @EntityGraph(attributePaths = {"product", "sender"})
    GiftHistory findGiftHistoryWithProductAndAccountById(Long id);
}
