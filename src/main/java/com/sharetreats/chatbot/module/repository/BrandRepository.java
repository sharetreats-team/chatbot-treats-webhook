package com.sharetreats.chatbot.module.repository;

import com.sharetreats.chatbot.module.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    @Query(value = "select * from brand a where a.category_id =?1", nativeQuery = true)
    List<Brand> findBrandsByCategory(Long category_id);
}
