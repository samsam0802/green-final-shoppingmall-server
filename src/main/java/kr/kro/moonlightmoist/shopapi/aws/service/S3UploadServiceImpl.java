package kr.kro.moonlightmoist.shopapi.aws.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class S3UploadServiceImpl implements S3UploadService{

    private final S3Client s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    @Value("${aws.s3.region}")
    private String region;

    @Override
    public String uploadOneFile(MultipartFile file, String path) { // path 예시 :  "products/"
        try {
            // 파일 유효성 검사
            if (file.isEmpty()) {
                throw new IllegalArgumentException("파일이 비어있습니다.");
            }

            // 파일명 생성
            String originalFileName = file.getOriginalFilename();
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String s3FileName = path + UUID.randomUUID() + fileExtension;

            // S3 업로드
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(s3FileName)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

            return String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, s3FileName);

        } catch (Exception e) {
            log.error("S3 파일 업로드 실패 : {} ", e.getMessage(), e); // e 는 무슨역할?
            throw new RuntimeException("S3 파일 업로드 실패", e);
        }
    }
}
