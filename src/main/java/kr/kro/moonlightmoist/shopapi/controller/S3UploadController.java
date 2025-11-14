package kr.kro.moonlightmoist.shopapi.controller;

import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/api/s3")
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*",
        methods = {org.springframework.web.bind.annotation.RequestMethod.GET,
                org.springframework.web.bind.annotation.RequestMethod.POST,
                org.springframework.web.bind.annotation.RequestMethod.PUT,
                org.springframework.web.bind.annotation.RequestMethod.DELETE,
                org.springframework.web.bind.annotation.RequestMethod.OPTIONS})
public class S3UploadController {

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    @Value("${aws.access-key-id}")
    private String accessKey;

    @Value("${aws.secret-access-key}")
    private String secretKey;

//    @Autowired
//    private S3Config s3Config;

    @Value("${aws.s3.region}")
    private String region;

    // S3 클라이언트 생성 (간단한 구현)
    private S3Client createS3Client() {
        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKey, secretKey)
                ))
                .build();
    }


    @PostMapping("/upload")
    public ResponseEntity<String> s3upload(@ModelAttribute MultipartFile file) {
//        log.info("s3upload:{}",dto);
//        List<MultipartFile> files = dto.getFiles();

        log.info("s3upload file : {}" , file);

        try {
            // 1. 파일 유효성 검사
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("파일이 비어있습니다.");
            }

            // 2. S3에 업로드할 고유 파일명 생성
            String originalFileName = file.getOriginalFilename();
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String s3FileName ="products/"+ UUID.randomUUID().toString() + fileExtension;

            // 3. S3 업로드
            S3Client s3Client = createS3Client();

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(s3FileName)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(putObjectRequest,
                    RequestBody.fromBytes(file.getBytes()));

            // 4. S3 URL 생성
            String s3Url = String.format("https://%s.s3.%s.amazonaws.com/%s",
                    bucketName, region, s3FileName);

            return ResponseEntity.ok(s3Url);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok("s3Url");
    }
}
