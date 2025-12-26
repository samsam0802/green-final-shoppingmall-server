package kr.kro.moonlightmoist.shopapi.payment.service;

public interface PaymentService {
//    void verifyPaymentAndCompleteOrder(String impUid, String merchantUid) throws Exception;
    void refundOrder(Long orderId, String reason) throws Exception;
}
