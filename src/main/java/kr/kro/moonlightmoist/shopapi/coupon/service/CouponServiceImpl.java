package kr.kro.moonlightmoist.shopapi.coupon.service;

import jakarta.persistence.EntityExistsException;
import kr.kro.moonlightmoist.shopapi.coupon.domain.Coupon;
import kr.kro.moonlightmoist.shopapi.coupon.dto.CouponDto;
import kr.kro.moonlightmoist.shopapi.coupon.dto.CouponSearchCondition;
import kr.kro.moonlightmoist.shopapi.coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService{

    private final CouponRepository couponRepository;

    @Override
    public Long register(CouponDto dto) {
        Coupon savedCoupon = couponRepository.save(dto.toEntity());

        return savedCoupon.getId();
    }

    @Override
    public List<CouponDto> searchCouponsByCondition(CouponSearchCondition condition) {
        List<Coupon> couponList = couponRepository.search(condition);
        List<CouponDto> dtos = couponList.stream().map(coupon -> coupon.toDto()).toList();
        return dtos;
    }

    @Override
    public CouponDto findCoupon(Long id) {
        CouponDto coupon = couponRepository.findById(id).get().toDto();
        return coupon;
    }

    @Override
    @Transactional
    public Long modifyCoupon(CouponDto dto) {
        Coupon coupon = couponRepository.findById(dto.getId()).get();
        coupon.changeCoupon(dto);
        return dto.getId();
    }

    @Override
    @Transactional
    public void deleteCoupon(Long id) {
        Coupon coupon = couponRepository.findById(id).orElseThrow(EntityExistsException::new);
        coupon.deleteCoupon();
    }
}
