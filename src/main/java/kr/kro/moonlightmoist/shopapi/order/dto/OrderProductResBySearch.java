package kr.kro.moonlightmoist.shopapi.order.dto;

import kr.kro.moonlightmoist.shopapi.order.domain.OrderProductStatus;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductResBySearch {
    private Long id;
    // 상품 이름
    private String productName;
    // 상품 옵션 이름
    private String productOptionName;
    // 상품 갯수
    private int quantity;
    // 상품 썸네일 이미지 url
    private String imageUrl;
    // 상품 총 금액(상품 갯수 x 상품 1개의 가격)
    private int totalAmount;
    // 상품 주문 상태(주문 접수, 결제 완료, 배송준비중, 배송중, 배송완료, 구매확정)
    private OrderProductStatus orderProductStatus;

    // 반품 사유
    private String returnReason;

    // 상품 id
    private Long productId;
}
