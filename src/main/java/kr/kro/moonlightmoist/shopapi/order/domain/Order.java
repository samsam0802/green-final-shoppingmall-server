package kr.kro.moonlightmoist.shopapi.order.domain;

import jakarta.persistence.*;
import kr.kro.moonlightmoist.shopapi.common.domain.BaseTimeEntity;
import kr.kro.moonlightmoist.shopapi.order.dto.OrderResBySearch;
import kr.kro.moonlightmoist.shopapi.policy.deliveryPolicy.domain.DeliveryPolicy;
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
    @Column(nullable = false)
    private String paymentMethod;
//    @ManyToOne
//    @JoinColumn(name="delivery_policy_id",nullable = true)
//    private DeliveryPolicy deliveryPolicy;
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

    @Column(name = "is_deleted", nullable = false)
    private boolean deleted;

    // 조회 전용
    @OneToOne
    private OrderCoupon orderCoupon;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @Builder.Default
    private List<OrderProduct> orderProducts = new ArrayList<>();

    public void applyOrderCoupon(OrderCoupon orderCoupon) {
        this.orderCoupon=orderCoupon;
    }

    public OrderResBySearch toDtoForOrderResBySearch() {
        OrderResBySearch orderRes = OrderResBySearch.builder()
                .id(this.getId())
                .orderDate(this.getCreatedAt().toLocalDate())
                .ordererName(this.user.getName())
                .receiverName(this.getReceiverName())
                .orderNumber(this.getOrderNumber())
                .paymentMethod(this.getPaymentMethod())
                .orderProducts(this.getOrderProducts().stream().map(op -> op.toDtoForOrderProductResBySearch()).toList())
                .build();
        return orderRes;
    }


}
