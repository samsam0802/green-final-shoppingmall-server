package kr.kro.moonlightmoist.shopapi.usercoupon.service;

public interface UserCouponService {
    Long issue(Long userId, Long couponId);
}
