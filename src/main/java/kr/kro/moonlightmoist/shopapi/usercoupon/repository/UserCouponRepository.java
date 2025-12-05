package kr.kro.moonlightmoist.shopapi.usercoupon.repository;

import kr.kro.moonlightmoist.shopapi.usercoupon.domain.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {
    List<UserCoupon> findByUser_Id(Long userId);
    Optional<UserCoupon> findByUser_IdAndCoupon_Id(Long userId, Long couponId);
}
