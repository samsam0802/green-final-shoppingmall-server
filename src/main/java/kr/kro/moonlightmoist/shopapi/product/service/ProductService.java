package kr.kro.moonlightmoist.shopapi.product.service;

import kr.kro.moonlightmoist.shopapi.product.dto.ProductRequest;

public interface ProductService {
    // 상품 등록
    Long register(ProductRequest dto);
    // 상품 조회
    // 상품 수정
    // 상품 삭제
}
