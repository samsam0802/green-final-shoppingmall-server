package kr.kro.moonlightmoist.shopapi.payment.controller;

import kr.kro.moonlightmoist.shopapi.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/payments")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class PaymentController {
    private final PaymentService paymentService;


//    @PostMapping("/verify")
//    public ResponseEntity<String> verifyPayment(@RequestBody Map<String, String> requestData) {
//        String impUid = requestData.get("imp_uid");
//        String merchantUid = requestData.get("merchant_uid");
//
//        try {
//            // Service의 검증 로직 호출
//            paymentService.verifyPaymentAndCompleteOrder(impUid, merchantUid);
//            return ResponseEntity.ok("결제 검증 및 주문 처리가 완료되었습니다.");
//        } catch (Exception e) {
//            // 검증 실패 시 400 에러 반환
//            return ResponseEntity.status(400).body("결제 검증 실패: " + e.getMessage());
//        }
//    }

    @PostMapping("/refund/{orderId}")
    public ResponseEntity<String> refundPayment(@PathVariable Long orderId, @RequestBody String reason) {
        try {
            paymentService.refundOrder(orderId, reason);
            return ResponseEntity.ok("환불 처리가 완료되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("환불 실패: " + e.getMessage());
        }
    }
}
