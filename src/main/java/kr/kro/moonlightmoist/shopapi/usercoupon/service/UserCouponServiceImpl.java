package kr.kro.moonlightmoist.shopapi.usercoupon.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import kr.kro.moonlightmoist.shopapi.coupon.domain.Coupon;
import kr.kro.moonlightmoist.shopapi.coupon.exception.InvalidCouponCodeException;
import kr.kro.moonlightmoist.shopapi.coupon.repository.CouponRepository;
import kr.kro.moonlightmoist.shopapi.user.domain.User;
import kr.kro.moonlightmoist.shopapi.user.repository.UserRepository;
import kr.kro.moonlightmoist.shopapi.usercoupon.domain.CouponUsageStatus;
import kr.kro.moonlightmoist.shopapi.usercoupon.domain.UserCoupon;
import kr.kro.moonlightmoist.shopapi.usercoupon.dto.UserCouponRes;
import kr.kro.moonlightmoist.shopapi.usercoupon.exception.DuplicationCouponIssueException;
import kr.kro.moonlightmoist.shopapi.usercoupon.repository.UserCouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserCouponServiceImpl implements UserCouponService{

    private final UserCouponRepository userCouponRepository;
    private final UserRepository userRepository;
    private final CouponRepository couponRepository;

    @Override
    public Long issue(Long userId, Long couponId) {
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(EntityNotFoundException::new);
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

    @Override
    @Transactional
    public void useCoupon(Long userId, Long couponId) {
        UserCoupon userCoupon = userCouponRepository.findByUserIdAndCouponId(userId, couponId).orElseThrow(EntityNotFoundException::new);
        userCoupon.useCoupon();
    }

    @Override
    @Transactional
    public void issueCouponByCode(Long userId, String code) {

        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        Coupon coupon = couponRepository.findByCouponCode(code).orElseThrow(InvalidCouponCodeException::new);

        // 이 회원이 해당 쿠폰을 이미 가지고 있는지 검증
        List<UserCoupon> userCoupons = userCouponRepository.findByUserId(userId);
        for (UserCoupon userCoupon : userCoupons) {
            if (Objects.equals(coupon.getId(), userCoupon.getCoupon().getId())) {
                throw new DuplicationCouponIssueException();
            }
        }

        coupon.addIssueCount();

        UserCoupon userCoupon = UserCoupon.builder()
                .user(user)
                .coupon(coupon)
                .build();

        userCouponRepository.save(userCoupon);
    }

    @Override
    @Transactional
    public void issueManualCoupons(List<Long> userIds, List<Long> couponIds) {

        for (Long userId : userIds ) {
            for (Long couponId : couponIds) {
                if (userCouponRepository.findByUserIdAndCouponId(userId, couponId).isPresent()) {
                    // 이미 해당 쿠폰을 가지고 있을 경우
                } else {
                    User user = userRepository.findById(userId).orElseThrow(EntityExistsException::new);
                    Coupon coupon = couponRepository.findById(couponId).orElseThrow(EntityNotFoundException::new);
                    UserCoupon userCoupon = UserCoupon.builder()
                            .user(user)
                            .coupon(coupon)
                            .build();
                    userCouponRepository.save(userCoupon);
                }
            }
        }
    }
}
