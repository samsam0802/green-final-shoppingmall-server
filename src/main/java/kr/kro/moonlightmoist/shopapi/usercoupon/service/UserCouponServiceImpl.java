package kr.kro.moonlightmoist.shopapi.usercoupon.service;

import kr.kro.moonlightmoist.shopapi.coupon.domain.Coupon;
import kr.kro.moonlightmoist.shopapi.coupon.repository.CouponRepository;
import kr.kro.moonlightmoist.shopapi.order.repository.OrderCouponRepository;
import kr.kro.moonlightmoist.shopapi.user.domain.User;
import kr.kro.moonlightmoist.shopapi.user.repository.UserRepository;
import kr.kro.moonlightmoist.shopapi.usercoupon.domain.CouponUsageStatus;
import kr.kro.moonlightmoist.shopapi.usercoupon.domain.UserCoupon;
import kr.kro.moonlightmoist.shopapi.usercoupon.dto.UserCouponRes;
import kr.kro.moonlightmoist.shopapi.usercoupon.repository.UserCouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserCouponServiceImpl implements UserCouponService{

    private final UserCouponRepository userCouponRepository;
    private final UserRepository userRepository;
    private final CouponRepository couponRepository;
    private final OrderCouponRepository orderCouponRepository;

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

        List<UserCoupon> userCoupons = userCouponRepository.findByUserId(userId);
        List<UserCouponRes> dtos = userCoupons.stream()
                .filter(coupon -> coupon.getUsageStatus().equals(CouponUsageStatus.ACTIVE))
                .map(i -> i.toDto()).toList();
        return dtos;
    }

    // 민석님이 사용할 결제시 쿠폰 used 로 반드는 메서드
    @Override
    @Transactional
    public void useCoupon(Long userId, Long couponId) {
        UserCoupon userCoupon = userCouponRepository.findByUserIdAndCouponId(userId, couponId).get();
        userCoupon.useCoupon();
    }
}
