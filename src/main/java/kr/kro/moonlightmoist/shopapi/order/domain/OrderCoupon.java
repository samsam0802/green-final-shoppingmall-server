package kr.kro.moonlightmoist.shopapi.order.domain;

import jakarta.persistence.*;
import kr.kro.moonlightmoist.shopapi.common.domain.BaseTimeEntity;
import kr.kro.moonlightmoist.shopapi.coupon.domain.DiscountType;
import kr.kro.moonlightmoist.shopapi.order.dto.OrderCouponResponseDTO;
import kr.kro.moonlightmoist.shopapi.order.dto.OrderResponseDTO;
import kr.kro.moonlightmoist.shopapi.usercoupon.domain.UserCoupon;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_coupons")
@AttributeOverride(name="createdAt", column = @Column(name = "applied_at",updatable = false,nullable = false))
public class OrderCoupon extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "order_id", unique = true, nullable = false)
    private Order order;
    @ManyToOne
    @JoinColumn(name = "user_coupon_id",nullable = false)
    private UserCoupon userCoupon;
    @Column(nullable = false)
    private int discountAmount;

    // 쿠폰 코드 스냅샷 (null 허용)
    @Column(nullable = true)
    private String couponCode;

    // 할인 타입 스냅샷
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DiscountType discountType;

    @Column(name = "is_deleted", nullable = false)
    private boolean deleted;

    public void deleteOrderCoupon() {
        this.deleted=true;
    }

    public OrderCouponResponseDTO toDto() {
        OrderCouponResponseDTO orderCouponResponseDTO = OrderCouponResponseDTO.builder()
                .id(this.getId())
                .discountAmount(this.getDiscountAmount())
                .discountType(this.getDiscountType().name())
                .couponCode(this.getCouponCode())
                .appliedAt(this.getCreatedAt())
                .userCouponName(this.getUserCoupon().getCoupon().getName())
                .build();
        return orderCouponResponseDTO;
    }

}
