package com.example.FixLog.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.FixLog.exception.CustomException;
import com.example.FixLog.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String upload(MultipartFile file, String dirName) {
        String key = generateKey(dirName, file.getOriginalFilename());

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        try (InputStream is = file.getInputStream()) {
            amazonS3.putObject(bucket, key, is, metadata);
        } catch (IOException e) {
            throw new CustomException(ErrorCode.S3_UPLOAD_FAILED);
        }

        return getObjectUrl(key);
    }

    public String generateKey(String dirName, String filename) {
        return dirName + "/" + UUID.randomUUID() + "_" + filename;
    }

    public String generatePresignedUrl(String dirName, String filename, int minutes) {
        String key = generateKey(dirName, filename);
        Date expiration = new Date(System.currentTimeMillis() + minutes * 60L * 1000L);

        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucket, key)
                .withMethod(HttpMethod.PUT)
                .withExpiration(expiration);

        URL url = amazonS3.generatePresignedUrl(request);
        return url.toString();
    }

    public String getObjectUrl(String key) {
        return amazonS3.getUrl(bucket, key).toString();
    }
}