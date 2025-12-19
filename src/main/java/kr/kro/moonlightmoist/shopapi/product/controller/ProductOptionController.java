package kr.kro.moonlightmoist.shopapi.product.controller;

import kr.kro.moonlightmoist.shopapi.product.dto.RestockOptionReq;
import kr.kro.moonlightmoist.shopapi.product.service.ProductOptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product_options")
@Slf4j
@RequiredArgsConstructor
public class ProductOptionController {

    private final ProductOptionService productOptionService;

    @PatchMapping("/restock")
    public ResponseEntity<String> restockOption(@RequestBody List<RestockOptionReq> dto) {
        if (dto == null || dto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("요청 데이터가 없습니다. 최소 하나의 재고 정보가 필요합니다.");
        }

        productOptionService.restockOptions(dto);

        return ResponseEntity.ok("ok");
    }
}
