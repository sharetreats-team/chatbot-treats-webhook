package com.sharetreats.chatbot.module.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3FileService {
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${brand-path}")
    private String brandDirName;

    @Value("${product-path}")
    private String productDirName;

    public String uploadImage(String domainName, MultipartFile multipartFile) throws IOException {

        String fileName = "";

        if (domainName.equals("brand")) {
            fileName = createBrandFilename(multipartFile.getOriginalFilename());
        }

        if (domainName.equals("product")) {
            fileName = createProductFilename(multipartFile.getOriginalFilename());
        }
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(multipartFile.getSize());

        try{
            amazonS3Client.putObject(
                    new PutObjectRequest(bucket, fileName, multipartFile.getInputStream(), objectMetadata)
            );
        } catch (IOException e){
            throw new IOException();
        }

        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    public String removeFile(String s3Url){
        String key = s3Url.substring(s3Url.indexOf("brand"));
        amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, key));
        return "이미지 파일이 삭제되었습니다.";
    }

    private String createBrandFilename(String originalFileName) {
        String type = originalFileName.substring(originalFileName.lastIndexOf("."));
        return brandDirName +"/"+ UUID.randomUUID().toString().concat(type);
    }

    private String createProductFilename(String originalFileName) {
        String type = originalFileName.substring(originalFileName.lastIndexOf("."));
        return productDirName +"/"+ UUID.randomUUID().toString().concat(type);
    }

    public void verifyFileType(String type) {
        String[] allowedTypeList = {"image/jpeg","image/jpg","image/png"};
        List<String> stringArrList = new ArrayList<>(Arrays.asList(allowedTypeList));
        if(!stringArrList.contains(type))
            throw new IllegalArgumentException("이미지 타입이 올바르지 않습니다.");
    }

}
