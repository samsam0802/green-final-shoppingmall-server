package kr.kro.moonlightmoist.shopapi.usercoupon.service;

import kr.kro.moonlightmoist.shopapi.coupon.domain.Coupon;
import kr.kro.moonlightmoist.shopapi.coupon.repository.CouponRepository;
import kr.kro.moonlightmoist.shopapi.user.domain.User;
import kr.kro.moonlightmoist.shopapi.user.repository.UserRepository;
import kr.kro.moonlightmoist.shopapi.usercoupon.domain.UserCoupon;
import kr.kro.moonlightmoist.shopapi.usercoupon.dto.UserCouponRes;
import kr.kro.moonlightmoist.shopapi.usercoupon.repository.UserCouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserCouponServiceImpl implements UserCouponService{

    private final UserCouponRepository userCouponRepository;
    private final UserRepository userRepository;
    private final CouponRepository couponRepository;

    @Override
    public Long issue(Long userId, Long couponId) {
        User user = userRepository.findById(userId).get();
        Coupon coupon = couponRepository.findById(couponId).get();
        coupon.addIssueCount();

        UserCoupon userCoupon = UserCoupon.builder()
                .user(user)
                .coupon(coupon)
                .build();

        UserCoupon savedUserCoupon = userCouponRepository.save(userCoupon);
        return savedUserCoupon.getId();
    }

    @Override
    public List<UserCouponRes> getUserCouponsByUserId(Long userId) {

        List<UserCoupon> userCoupons = userCouponRepository.findByUser_Id(userId);
        List<UserCouponRes> dtos = userCoupons.stream()
                .map(i -> i.toDto()).toList();
        return dtos;
    }
}
