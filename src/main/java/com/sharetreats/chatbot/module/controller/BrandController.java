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
