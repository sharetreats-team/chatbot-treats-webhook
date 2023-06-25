package com.sharetreats.chatbot.product.repository;

import com.sharetreats.chatbot.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByBrandName(String brandName);
}
