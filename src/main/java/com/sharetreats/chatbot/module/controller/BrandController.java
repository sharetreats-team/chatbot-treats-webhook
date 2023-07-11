package com.sharetreats.chatbot.module.controller;

import com.sharetreats.chatbot.module.controller.dto.brandDtos.BrandRequest;
import com.sharetreats.chatbot.module.service.BrandService;
import com.sharetreats.chatbot.module.service.S3FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
/**
 * 저희 서버 DB에서 브랜드 정보를 관리하기 위해 사용하는 API입니다.
 * @BrandRequest : 브랜드 정보 등록을 위한 이름, 이미지 URL 값을 담은 JSON 형식
 */
@RestController
@RequiredArgsConstructor
public class BrandController {

    private final S3FileService s3FileService;
    private final BrandService brandService;

    @PostMapping(value = "/viber/bot/brands")
    public ResponseEntity registerBrand(@RequestBody BrandRequest request) {
        return ResponseEntity.ok(brandService.registerBrand(request));
    }

    @PostMapping(value = "/viber/bot/brands/images")
    public ResponseEntity uploadFile(@RequestPart(value = "file") MultipartFile multipartFile) throws IOException {
        return new ResponseEntity(s3FileService.uploadImage("brand", multipartFile), HttpStatus.OK);
    }

    @DeleteMapping(value = "/viber/bot/brands/images")
    public ResponseEntity removeFile(@RequestParam String s3Url) throws IOException {
        return new ResponseEntity(s3FileService.removeFile(s3Url), HttpStatus.OK);
    }
}
