package com.sharetreats.chatbot.module.repository;

import com.sharetreats.chatbot.module.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}
