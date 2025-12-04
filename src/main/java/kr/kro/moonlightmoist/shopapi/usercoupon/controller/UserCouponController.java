package kr.kro.moonlightmoist.shopapi.usercoupon.controller;

import kr.kro.moonlightmoist.shopapi.usercoupon.dto.CouponIssueReq;
import kr.kro.moonlightmoist.shopapi.usercoupon.dto.UserCouponRes;
import kr.kro.moonlightmoist.shopapi.usercoupon.repository.UserCouponRepository;
import kr.kro.moonlightmoist.shopapi.usercoupon.service.UserCouponService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usercoupon")
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*",
        methods = {RequestMethod.GET,
                RequestMethod.POST,
                RequestMethod.PUT,
                RequestMethod.DELETE,
                RequestMethod.OPTIONS})
@RequiredArgsConstructor
public class UserCouponController {

    private final UserCouponService userCouponService;

    @PostMapping("")
    public ResponseEntity<String> issueCoupon(@RequestBody CouponIssueReq dto) {
        System.out.println("dto = " + dto);
        Long userCouponId = userCouponService.issue(dto.getUserId(), dto.getCouponId());
        System.out.println("userCouponId = " + userCouponId);
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<UserCouponRes>> getUserCoupons(@PathVariable Long userId) {
        List<UserCouponRes> userCoupons = userCouponService.getUserCouponsByUserId(userId);
        System.out.println("userCoupons = " + userCoupons);
        return ResponseEntity.ok(userCoupons);
    }
}
