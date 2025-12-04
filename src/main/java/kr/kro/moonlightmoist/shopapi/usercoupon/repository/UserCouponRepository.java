package kr.kro.moonlightmoist.shopapi.usercoupon.repository;

import kr.kro.moonlightmoist.shopapi.usercoupon.domain.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {
    List<UserCoupon> findByUser_Id(Long userId);
}
