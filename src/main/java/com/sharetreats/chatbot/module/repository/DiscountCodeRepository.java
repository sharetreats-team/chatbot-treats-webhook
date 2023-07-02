package com.sharetreats.chatbot.module.repository;

import com.sharetreats.chatbot.module.entity.DiscountCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountCodeRepository extends JpaRepository<DiscountCode, Long> {
    DiscountCode findByCode(String discountCode);
}