package kr.kro.moonlightmoist.shopapi.usercoupon.dto;

import kr.kro.moonlightmoist.shopapi.usercoupon.domain.CouponUsageStatus;
import kr.kro.moonlightmoist.shopapi.coupon.dto.CouponDto;
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
