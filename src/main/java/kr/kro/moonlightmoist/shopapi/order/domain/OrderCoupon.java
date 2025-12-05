package kr.kro.moonlightmoist.shopapi.order.domain;

import jakarta.persistence.*;
import kr.kro.moonlightmoist.shopapi.common.domain.BaseTimeEntity;
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
    @JoinColumn(name = "order_id",nullable = false)
    private Order order;
    @OneToOne
    @JoinColumn(name = "user_coupon_id",nullable = false)
    private UserCoupon userCoupon;
//    @Column(nullable = false)
//    private String couponCode;
    @Column(nullable = false)
    private int discountAmount;

}
