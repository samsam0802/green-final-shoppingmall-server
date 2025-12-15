package kr.kro.moonlightmoist.shopapi.usercoupon.domain;

import jakarta.persistence.*;
import kr.kro.moonlightmoist.shopapi.common.domain.BaseTimeEntity;
import kr.kro.moonlightmoist.shopapi.coupon.domain.Coupon;
import kr.kro.moonlightmoist.shopapi.user.domain.User;
import kr.kro.moonlightmoist.shopapi.usercoupon.dto.UserCouponRes;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "user_coupons")
public class UserCoupon extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private CouponUsageStatus usageStatus = CouponUsageStatus.ACTIVE;

    @Column(nullable = true)
    private LocalDateTime usedAt;

    @Column(name = "is_deleted", nullable = false)
    @Builder.Default
    private Boolean deleted = false;

    public void useCoupon() {
        this.usageStatus = CouponUsageStatus.USED;
    }

    public void recoverCoupon() {
        this.usageStatus = CouponUsageStatus.ACTIVE;
    }

    public UserCouponRes toDto() {
        return UserCouponRes.builder()
                .id(this.id)
                .coupon(this.coupon.toDto())
                .usageStatus(this.usageStatus)
                .usedAt(this.usedAt)
                .deleted(this.deleted)
                .createdAt(this.getCreatedAt())
                .build();
    }
}
