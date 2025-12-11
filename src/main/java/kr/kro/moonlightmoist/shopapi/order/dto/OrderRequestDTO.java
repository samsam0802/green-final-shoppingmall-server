package kr.kro.moonlightmoist.shopapi.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO { // 주문 요청 DTO
    private String paymentMethod;

    //배송정보
    private String receiverName;
    private String receiverPhone;
    private String postalCode;
    private String streetAddress;
    private String detailedAddress;
    private String deliveryRequest;

    private Long userId;
    private Long userCouponId; // 사용한 유저 쿠폰 아이디
    private int usedPoints; // 사용 포인트
    private int earnedPoints; // 적립된 포인트

    // 상품 옵션 ID + 수량
    private List<OrderProductRequestDTO> orderProducts;



}
