package kr.kro.moonlightmoist.shopapi.usercoupon.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CouponIssueReq {
    private Long userId;
    private Long couponId;
}
