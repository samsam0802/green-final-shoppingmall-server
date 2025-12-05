package kr.kro.moonlightmoist.shopapi.usercoupon.service;

import kr.kro.moonlightmoist.shopapi.usercoupon.dto.UserCouponRes;

import java.util.List;

public interface UserCouponService {
    Long issue(Long userId, Long couponId);
    List<UserCouponRes> getUserCouponsByUserId(Long userId);
    void useCoupon(Long userId, Long couponId);
}
