package kr.kro.moonlightmoist.shopapi.order.dto;

import kr.kro.moonlightmoist.shopapi.product.domain.ProductMainImage;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductResponseDTO {
    private Long id;
    // 브랜드명
    private String brandName;
    // 상품 이름
    private String productName;
    // 상품 옵션 이름
    private String productOptionName;
    // 상품 1개 가격
    private int purchasedPrice;
    // 상품 갯수
    private int quantity;
    // 상품 썸네일 이미지 url
    private ProductMainImage imageUrl;
}
