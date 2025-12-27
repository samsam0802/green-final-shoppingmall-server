package kr.kro.moonlightmoist.shopapi.order.domain;

import jakarta.persistence.*;
import kr.kro.moonlightmoist.shopapi.common.domain.BaseTimeEntity;
import kr.kro.moonlightmoist.shopapi.order.dto.OrderCouponResponseDTO;
import kr.kro.moonlightmoist.shopapi.order.dto.OrderProductResponseDTO;
import kr.kro.moonlightmoist.shopapi.order.dto.OrderResBySearch;
import kr.kro.moonlightmoist.shopapi.order.dto.OrderResponseDTO;
import kr.kro.moonlightmoist.shopapi.policy.deliveryPolicy.domain.DeliveryPolicy;
import kr.kro.moonlightmoist.shopapi.product.domain.ImageType;
import kr.kro.moonlightmoist.shopapi.product.domain.ProductMainImage;
import kr.kro.moonlightmoist.shopapi.user.domain.User;
import lombok.*;
import org.hibernate.annotations.Check;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString(exclude = {"user", "orderProducts"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Check(constraints = "payment_method IN ('CARD', 'BANK', 'PHONE', 'KAKAO', 'NAVER', 'PAYCO')")
@Table(name="orders")
public class Order extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="user_id",nullable = false)
    private User user;
    @Column(unique = true, nullable = false)
    private String orderNumber;
    // 포트원 시스템에서 생성한 결제 고유 번호
    @Column(unique = true, nullable = true)
    private String impUid;
    @Column(nullable = false)
    private String paymentMethod;
    //예상 배송일
    @Column(nullable = false)
    private LocalDate expectedDeliveryDate;
    //총 상품 금액
    @Column(nullable = false)
    private int totalProductAmount;
    //배송비(스냅샷)
    @Column(nullable = false)
    private int deliveryFee;
    //쿠폰 할인 금액
    @Column(nullable = false)
    private int discountAmount;
    //사용된 포인트
    @Column(nullable = false)
    private int usedPoints;
    //적립 포인트
    @Column(nullable = false)
    private int earnedPoints;
    //최종 결제 금액
    @Column(nullable = false)
    private int finalAmount;
    @Column(nullable = false)
    private String receiverName;
    @Column(nullable = false)
    private String receiverPhone;
    @Column(nullable = false)
    private String postalCode;
    @Column(nullable = false)
    private String streetAddress;
    @Column(nullable = false)
    private String detailedAddress;
    //배송 요청사항
    @Column(nullable = true)
    private String deliveryRequest;

    // 결제한 날짜
    @Column(nullable = true)
    private LocalDateTime paidAt;

    @Column(name = "is_deleted", nullable = false)
    private boolean deleted;

    // 조회 전용 //(mappedBy = "order", fetch = FetchType.LAZY) 추가 by 병국
    @OneToOne(mappedBy = "order", fetch = FetchType.LAZY)
    private OrderCoupon orderCoupon;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @Builder.Default
    private List<OrderProduct> orderProducts = new ArrayList<>();

    public void applyOrderCoupon(OrderCoupon orderCoupon) {
        this.orderCoupon=orderCoupon;
    }

    public void applyImpUid(String impUid) {this.impUid=impUid;}

    public void decidePaidAt(LocalDateTime paidAt) {this.paidAt=paidAt;}

    public void deleteOrder() {
        this.deleted=true;
        for(OrderProduct orderProduct : this.getOrderProducts()) {
         orderProduct.deleteOrderProduct();
        }
    }

    public OrderResponseDTO toDto() {
        OrderCoupon orderCoupon = this.getOrderCoupon();
        OrderCouponResponseDTO orderCouponResponseDTO = null;
        if(orderCoupon != null) {
            orderCouponResponseDTO = orderCoupon.toDto();
        }

        OrderResponseDTO orderResponseDTO = OrderResponseDTO.builder()
                .id(this.getId())
                .orderNumber(this.getOrderNumber())
                .deliveryFee(this.getDeliveryFee())
                .deliveryRequest(this.getDeliveryRequest())
                .detailedAddress(this.getDetailedAddress())
                .discountAmount(this.getDiscountAmount())
                .usedPoints(this.getUsedPoints())
                .earnedPoints(this.getEarnedPoints())
                .finalAmount(this.getFinalAmount())
                .expectedDeliveryDate(this.getExpectedDeliveryDate())
                .receiverName(this.getReceiverName())
                .receiverPhone(this.getReceiverPhone())
                .streetAddress(this.getStreetAddress())
                .paymentMethod(this.getPaymentMethod())
                .totalProductAmount(this.getTotalProductAmount())
                .postalCode(this.getPostalCode())
                .orderDate(this.getCreatedAt().toLocalDate())
                .orderCoupon(orderCouponResponseDTO)
                .build();


        for(OrderProduct op : this.getOrderProducts()){
            OrderProductResponseDTO orderProductResponseDTO = OrderProductResponseDTO.builder()
                    .id(op.getId())
                    .productId(op.getProductOption().getProduct().getId())
                    .brandName(op.getProductOption().getProduct().getBrand().getName())
                    .productName(op.getProductOption().getProduct().getBasicInfo().getProductName())
                    .productOptionName(op.getProductOption().getOptionName())
                    .purchasedPrice(op.getPurchasedPrice())
                    .quantity(op.getQuantity())
                    .imageUrl(op.getProductOption().getProduct().getMainImages().stream().filter(image->image.getImageType() == ImageType.THUMBNAIL).map(ProductMainImage::getImageUrl).findFirst().orElse(null))
                    .orderProductStatus(op.getOrderProductStatus())
                    .build();
            orderResponseDTO.getOrderProducts().add(orderProductResponseDTO);
        }
        return orderResponseDTO;
    }

    public OrderResBySearch toDtoForOrderResBySearch() {
        OrderResBySearch orderRes = OrderResBySearch.builder()
                .id(this.getId())
                .orderDate(this.getCreatedAt().toLocalDate())
                .orderNumber(this.getOrderNumber())
                .impUid(this.getImpUid())
                .orderProducts(this.getOrderProducts().stream().map(op -> op.toDtoForOrderProductResBySearch()).toList())
                .receiverName(this.getReceiverName())
                .ordererName(this.user.getName())
                .paymentMethod(this.getPaymentMethod())
                .earnedPoints(this.getEarnedPoints())
                .build();
        return orderRes;
    }


}
