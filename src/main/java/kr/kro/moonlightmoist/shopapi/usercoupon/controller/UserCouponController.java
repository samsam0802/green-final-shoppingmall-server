package kr.kro.moonlightmoist.shopapi.usercoupon.controller;

import kr.kro.moonlightmoist.shopapi.usercoupon.dto.CouponIssueReq;
import kr.kro.moonlightmoist.shopapi.usercoupon.dto.CouponsIssueReq;
import kr.kro.moonlightmoist.shopapi.usercoupon.dto.UserCouponRes;
import kr.kro.moonlightmoist.shopapi.usercoupon.service.UserCouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usercoupon")
@Slf4j
@RequiredArgsConstructor
public class UserCouponController {

    private final UserCouponService userCouponService;

    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @PostMapping("")
    public ResponseEntity<String> issueCoupon(@RequestBody CouponIssueReq dto) {
        System.out.println("dto = " + dto);
        Long userCouponId = userCouponService.issue(dto.getUserId(), dto.getCouponId());
        System.out.println("userCouponId = " + userCouponId);
        return ResponseEntity.ok("ok");
    }

    @PreAuthorize("hasAnyRole('USER','MANAGER','ADMIN')")
    @GetMapping("/{userId}")
    public ResponseEntity<List<UserCouponRes>> getUserCoupons(@PathVariable Long userId) {
        List<UserCouponRes> userCoupons = userCouponService.getUserCouponsByUserId(userId);
        System.out.println("userCoupons = " + userCoupons);
        return ResponseEntity.ok(userCoupons);
    }

    @PreAuthorize("hasAnyRole('USER','MANAGER','ADMIN')")
    @PostMapping("/code")
    public ResponseEntity<String> issueCouponByCode(@RequestBody CouponIssueReq dto) {
        System.out.println("userId = " + dto.getUserId());
        System.out.println("couponCode = " + dto.getCouponCode());

        userCouponService.issueCouponByCode(dto.getUserId(), dto.getCouponCode());
        return ResponseEntity.ok("ok");
    }

    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @PostMapping("/manual")
    public ResponseEntity<String> issueManualCoupon(@RequestBody CouponsIssueReq dto) {
        System.out.println("userId = " + dto.getUserIds());
        System.out.println("couponIds = " + dto.getCouponIds());

        userCouponService.issueManualCoupons(dto.getUserIds(), dto.getCouponIds());
        return ResponseEntity.ok("ok");
    }
}
