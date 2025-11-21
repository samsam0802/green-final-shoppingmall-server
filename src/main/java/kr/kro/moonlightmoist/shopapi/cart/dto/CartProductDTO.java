package kr.kro.moonlightmoist.shopapi.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 상품 등록, 수량 변경, 삭제할 때 필요
// 요청 DTO
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class CartProductDTO {
    //User Id
    private Long userId;
    //CartProduct Id
    private Long id;
    private Long productOptionId;
    private int quantity;
}
