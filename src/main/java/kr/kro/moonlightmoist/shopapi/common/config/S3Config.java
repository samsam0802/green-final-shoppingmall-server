package kr.kro.moonlightmoist.shopapi.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    @Value("${aws.access-key-id}")
    private String accessKey;

    @Value("${aws.secret-access-key}")
    private String secretKey;

    @Value("${aws.s3.region}")
    private String region;

    // S3 클라이언트 생성 (간단한 구현)
    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKey, secretKey)
                ))
                .build();
    }

    // VaultTestController 에서 쓰던 것 주석처리
//    @Value("${bucket-name:default-name}")
//    private String bucketName;
//
//    @Value("${access-key-id:default-key-id}")
//    private String accessKeyId;
//
//    @Value("${secret-access-key:default-access-key}")
//    private String secretAccessKey;

//    public String getBucketName() { return bucketName; }
//    public String getAccessKeyId() { return accessKeyId; }
//    public String getSecretAccessKey() { return secretAccessKey; }
}
