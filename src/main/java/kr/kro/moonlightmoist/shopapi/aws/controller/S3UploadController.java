package kr.kro.moonlightmoist.shopapi.aws.controller;

import kr.kro.moonlightmoist.shopapi.aws.service.S3UploadService;
import kr.kro.moonlightmoist.shopapi.common.config.S3Config;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/s3")
@Slf4j
public class S3UploadController {

    @Autowired
    private final S3UploadService s3UploadService;

    @PostMapping("/upload")
    public ResponseEntity<String> s3upload(@ModelAttribute MultipartFile file) {

        log.info("s3upload file : {}" , file);
        String s3url = s3UploadService.uploadOneFile(file, "products/");

        return ResponseEntity.ok(s3url);
    }
}
