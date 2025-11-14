package kr.kro.moonlightmoist.shopapi.controller;

import kr.kro.moonlightmoist.shopapi.config.S3Config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*",
        methods = {org.springframework.web.bind.annotation.RequestMethod.GET,
                org.springframework.web.bind.annotation.RequestMethod.POST,
                org.springframework.web.bind.annotation.RequestMethod.PUT,
                org.springframework.web.bind.annotation.RequestMethod.DELETE,
                org.springframework.web.bind.annotation.RequestMethod.OPTIONS})
//@VaultPropertySource("secret/api-server/s3")
public class VaultTestController {

    @Autowired
    private S3Config s3Config;

    @Value("${aws.s3.region}")
    private String region;

    @GetMapping("/api/vault")
    public ResponseEntity<String> vaultTest() {

        System.out.println("bucket-name : "+ s3Config.getBucketName());
        System.out.println("access-key : "+ s3Config.getAccessKeyId());
        System.out.println("secret-access-key : "+ s3Config.getSecretAccessKey());

        return ResponseEntity.ok("ok");
    }
}
