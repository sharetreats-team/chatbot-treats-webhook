package com.sharetreats.chatbot.module.repository;

import com.sharetreats.chatbot.module.entity.Brand;
import com.sharetreats.chatbot.module.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByBrand(Brand brand);
}
