package kr.kro.moonlightmoist.shopapi.usercoupon.dto;

import jakarta.persistence.*;
import kr.kro.moonlightmoist.shopapi.coupon.domain.Coupon;
import kr.kro.moonlightmoist.shopapi.coupon.domain.CouponUsageStatus;
import kr.kro.moonlightmoist.shopapi.coupon.dto.CouponDto;
import kr.kro.moonlightmoist.shopapi.user.domain.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserCouponRes {
    private Long id;
    private CouponDto coupon;
    private CouponUsageStatus usageStatus;
    private LocalDateTime usedAt;
    private Boolean deleted;
    private LocalDateTime createdAt;
}
