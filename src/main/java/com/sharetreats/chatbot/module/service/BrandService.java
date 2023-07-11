package com.sharetreats.chatbot.module.service;

import com.sharetreats.chatbot.module.controller.dto.brandDtos.BrandButtons;
import com.sharetreats.chatbot.module.controller.dto.brandDtos.BrandRequest;
import com.sharetreats.chatbot.module.entity.Brand;
import com.sharetreats.chatbot.module.option.Status;
import com.sharetreats.chatbot.module.repository.BrandRepository;
import com.sharetreats.chatbot.module.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;

    public String registerBrand(BrandRequest request) {
        Brand newBrand = Brand.builder()
                .name(request.getName())
                .status(Status.ACTIVE)
                .image(request.getImage().toString())
                .category(categoryRepository.findById(request.getCategoryId())
                        .orElseThrow(() ->new IllegalArgumentException()))
                .build();
        brandRepository.save(newBrand);
        return "브랜드 등록이 완료되었습니다.";
    }

    public List<BrandButtons> createBrandButtons(Long categoryId) {
        List<Brand> brandList;
        if (categoryId == 1) {
            brandList = brandRepository.findAll();
        } else {
            brandList = brandRepository.findBrandsByCategory(categoryId);
        }
        return brandList.stream()
                .map(b -> new BrandButtons(2,2,
                        "<br><font color=\\\"#494E67\\\"><b>" + b.getName() + "</b></font>",
                        "small", "center", "bottom", "reply",
                        "brandId " + b.getId().toString(), "#FFFFFF", "crop", "crop", b.getImage()))
                .collect(Collectors.toList());
    }
}
