package com.sharetreats.chatbot.module.repository;

import com.sharetreats.chatbot.module.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
