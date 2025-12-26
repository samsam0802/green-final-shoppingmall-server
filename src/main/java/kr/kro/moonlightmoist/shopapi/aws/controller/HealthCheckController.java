package kr.kro.moonlightmoist.shopapi.aws.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/health")
@Slf4j
public class HealthCheckController {

    @GetMapping("")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Success Health Check");
    }
}
