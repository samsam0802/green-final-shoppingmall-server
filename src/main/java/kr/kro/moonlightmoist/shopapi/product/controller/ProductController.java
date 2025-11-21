package kr.kro.moonlightmoist.shopapi.product.controller;

import kr.kro.moonlightmoist.shopapi.product.dto.ProductRequest;
import kr.kro.moonlightmoist.shopapi.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/products")
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*",
        methods = {RequestMethod.GET,
                RequestMethod.POST,
                RequestMethod.PUT,
                RequestMethod.DELETE,
                RequestMethod.OPTIONS})
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @PostMapping("")
    public ResponseEntity<String> productRegister(@RequestBody ProductRequest request) {

        System.out.println("request = " + request);

        Long id = productService.register(request);

        System.out.println("id = " + id);

        return ResponseEntity.ok("ok");
    }
}
