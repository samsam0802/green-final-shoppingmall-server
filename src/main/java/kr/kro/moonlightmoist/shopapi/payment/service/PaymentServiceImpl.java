package kr.kro.moonlightmoist.shopapi.payment.service;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import jakarta.annotation.PostConstruct;
import kr.kro.moonlightmoist.shopapi.order.domain.Order;
import kr.kro.moonlightmoist.shopapi.order.repository.OrderRepository;
import kr.kro.moonlightmoist.shopapi.order.service.OrderService;
import kr.kro.moonlightmoist.shopapi.pointHistory.service.PointHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private IamportClient iamportClient;

    private final OrderRepository orderRepository;

    private final OrderService orderService;
    private final PointHistoryService pointHistoryService;

    @Value("${portone.rest-api-key}")
    private String restApiKey;

    @Value("${portone.rest-api-secret}")
    private String restApiSecret;

    @PostConstruct
    public void init() {
        log.info("restApiKey:{}, restApiSecret:{}",restApiKey,restApiSecret);
        this.iamportClient = new IamportClient(restApiKey, restApiSecret);
        log.info("PortOne IamportClient 초기화 완료.");
    }

//    @Override
//    @Transactional
//    public void verifyPaymentAndCompleteOrder(String impUid, String merchantUid) throws Exception {
//        // A. PortOne에 실제 결제 정보 요청
//        // 이 메서드가 내부적으로 액세스 토큰을 발급(인증)하고 결제 정보를 조회합니다.
//        IamportResponse<Payment> paymentResponse = iamportClient.paymentByImpUid(impUid);
//        Payment payment = paymentResponse.getResponse();
//
//        if(payment == null) {
//            throw new IllegalArgumentException("결제 정보를 찾을 수 없습니다. imp_uid: " + impUid);
//        }
//
//        // B. 쇼핑몰 DB의 주문 정보 조회
//        BigDecimal expectedAmount = orderService.getExpectedAmount(merchantUid);
//
//        // C. 금액 검증(위변조 방지)
//        if(payment.getAmount().compareTo(expectedAmount) != 0) {
//            log.error("결제 금액 불일치. DB 금액: {}, 실제 결제 금액: {}", expectedAmount, payment.getAmount());
//
//            // 결제 취소
//            try{
//                CancelData cancelData = new CancelData(impUid,true); // true = 전액취소
//                cancelData.setReason("결제 금액 불일치로 인한 자동 취소"); // 취소 사유 (필수)
//
//                //보안상 금액 불일치 시, 즉시 결제 취소 API를 호출해야 합니다.
//                IamportResponse<Payment> cancelResponse = iamportClient.cancelPaymentByImpUid(cancelData);
//
//                if(cancelResponse.getCode() == 0) {
//                    log.info("결제 취소 완료: {}", cancelResponse.getResponse().getStatus());
//                } else {
//                    log.error("결제 취소 실패. 응답코드: {}, 메시지: {}",
//                            cancelResponse.getCode(), cancelResponse.getMessage());
//                    // Todo: 관리자 알림 필요
//                }
//
//            } catch (IamportResponseException | IOException e) {
//                log.error("결제 취소 API 호출 실패. imp_uid: {}, 에러: {}", impUid, e.getMessage());
//                //todo : 관리자 알림 필요
//            }
//
//            throw new IllegalStateException("결제 금액이 일치하지 않습니다.");
//        }
//
//        // D. 최종 상태 확인
//        if (!"paid".equals(payment.getStatus())) {
//            throw new IllegalStateException("결제 상태가 'paid'가 아닙니다. (status: " + payment.getStatus() + ")");
//        }
//
//        // E. DB 주문 상태 업데이트 (최종 완료)
//        orderService.completeOrder(merchantUid, impUid);
//
//        log.info("결제 검증 및 주문 완료. 주문번호: {}, imp_uid: {}", merchantUid, impUid);
//    }

    @Override
    @Transactional
    public void refundOrder(Long orderId, String reason) throws Exception {
        Order order = orderRepository.findById(orderId).get();

        // 1. 사전 검증
        // 예외 발생 시 여기서 중단되므로 포트원 API를 호출하지 않음 (안전)
        orderService.checkRefundable(order.getOrderNumber(), order.getUser().getId());

        // 2. DB에서 환불에 필요한 imp_uid와 결제 금액 가져오기
        String impUid = order.getImpUid();

        try {
            // 3. 포트원 환불 요청
            CancelData cancelData = new CancelData(impUid, true);  // imp_uid로 취소하는 것이 가장 정확함
            cancelData.setReason(reason);
//            cancelData.setChecksum(BigDecimal.valueOf(1)); // 위변조 방지 : 현재 DB의 잔액을 전달

            IamportResponse<Payment> response = iamportClient.cancelPaymentByImpUid(cancelData);

            if(response.getCode() == 0) {
                // 환불이 완료되면
                // 주문 삭제(쿠폰 복구, 주문 쿠폰, 주문, 주문 상품 삭제, 재고 수량 다시 증가)
                orderService.deleteOneOrder(order.getId());
                // 포인트 롤백
                pointHistoryService.rollbackPoint(order.getId());
            } else {
                throw new IllegalStateException("PG사 환불 실패: " + response.getMessage());
            }
        } catch (Exception e) {
            log.error("환불 통신 중 오류 발생", e);
            throw e;
        }
    }
}
