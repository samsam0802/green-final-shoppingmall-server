package kr.kro.moonlightmoist.shopapi.coupon.service;

import kr.kro.moonlightmoist.shopapi.coupon.dto.CouponDto;
import kr.kro.moonlightmoist.shopapi.coupon.dto.CouponSearchCondition;

import java.util.List;

public interface CouponService {
    Long register(CouponDto dto);
    List<CouponDto> searchCouponsByCondition(CouponSearchCondition condition);
    CouponDto findCoupon(Long id);
    Long modifyCoupon(CouponDto dto);
    void deleteCoupon(Long id);
}
