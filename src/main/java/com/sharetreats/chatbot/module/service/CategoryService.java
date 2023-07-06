package com.sharetreats.chatbot.module.service;

import com.sharetreats.chatbot.module.controller.dto.categoryDtos.CategoryButtons;
import com.sharetreats.chatbot.module.entity.Category;
import com.sharetreats.chatbot.module.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<CategoryButtons> createCategoryButtons() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(b -> new CategoryButtons(2, 2,
                        "<br><font color=\\\"#494E67\\\">" + b.getCategoryName() + "</font>",
                        "large", "center", "middle", "reply",
                        "categoryId " + b.getId().toString(), b.getColor(), "crop"))
                .collect(Collectors.toList());
    }
}
