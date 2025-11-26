package kr.kro.moonlightmoist.shopapi.product.service;

import kr.kro.moonlightmoist.shopapi.product.dto.ProductImagesUrlDTO;
import kr.kro.moonlightmoist.shopapi.product.dto.ProductRequest;
import kr.kro.moonlightmoist.shopapi.product.dto.ProductRes;

import java.util.List;

public interface ProductService {
    // 상품 등록
    Long register(ProductRequest dto);
    // s3 에 업로드한 이미지 url 추가
    void addImageUrls(Long id, ProductImagesUrlDTO dto);
    // 카테고리별 상품 조회
    List<ProductRes> searchProductsByCategory(List<Long> depth3CategoryIds);
    // 상품 수정
    // 상품 삭제
}
