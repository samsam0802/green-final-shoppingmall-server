package kr.kro.moonlightmoist.shopapi.coupon.controller;

import kr.kro.moonlightmoist.shopapi.coupon.dto.CouponDto;
import kr.kro.moonlightmoist.shopapi.coupon.dto.CouponSearchCondition;
import kr.kro.moonlightmoist.shopapi.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coupon")
@Slf4j
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @PostMapping("")
    public ResponseEntity<String> registerCoupon(@RequestBody CouponDto dto) {

        System.out.println("dto = " + dto);
        Long id = couponService.register(dto);
        System.out.println("id = " + id);

        return ResponseEntity.ok("ok");
    }

    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @PostMapping("/search")
    public ResponseEntity<List<CouponDto>> searchCoupons(@RequestBody CouponSearchCondition condition) {
        System.out.println("condition = " + condition);
        List<CouponDto> coupons = couponService.searchCouponsByCondition(condition);
        return ResponseEntity.ok(coupons);
    }

    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<CouponDto> getCouponData(@PathVariable("id") Long id) {
        System.out.println("id = " + id);
        CouponDto coupon = couponService.findCoupon(id);
        return ResponseEntity.ok(coupon);
    }

    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @PostMapping("/modify")
    public ResponseEntity<Long> modifyCoupon(@RequestBody CouponDto dto) {

        Long id = couponService.modifyCoupon(dto);

        return ResponseEntity.ok(id);
    }

    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCoupon(@PathVariable("id") Long id) {
        couponService.deleteCoupon(id);
        return ResponseEntity.ok("ok");
    }

    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @GetMapping("/manual")
    public ResponseEntity<List<CouponDto>> findManualCoupons() {
        List<CouponDto> coupons = couponService.getManualCoupons();
        return ResponseEntity.ok(coupons);
    }

}
