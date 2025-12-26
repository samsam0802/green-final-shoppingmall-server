package kr.kro.moonlightmoist.shopapi.order.service;

import kr.kro.moonlightmoist.shopapi.order.domain.OrderProductStatus;
import kr.kro.moonlightmoist.shopapi.order.dto.*;
import kr.kro.moonlightmoist.shopapi.review.dto.PageRequestDTO;
import kr.kro.moonlightmoist.shopapi.review.dto.PageResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public interface OrderService {
    Long createOrder(OrderRequestDTO dto, Long userId);
    OrderResponseDTO getOneOrder(Long orderId);
//    PageResponseDTO<OrderResponseDTO> getOrderList(Long userId, String sort, PageRequestDTO pageRequestDTO);
    // 결제 금액과 db에 저장된 주문 총 금액이 일치하는지 확인하기 위해 필요한 메서드
//    BigDecimal getExpectedAmount(String merchantUid);
    // 결제 검증이 끝나고 주문 상품 상태를 결제 완료로 변경
    void completeOrder(String merchantUid, String impUid);
    void deleteOneOrder(Long orderId);
    PageResponseDTO<OrderResBySearch> searchOrdersByCondition(OrderSearchCondition condition, String sort, PageRequestDTO pageRequestDTO);
    void comfirmOrder(Long orderId);
    void changeOrderProductStatus(Long orderId, OrderProductStatus status, String reason);
    void checkRefundable(String merchantUid, Long currentUserId);
    Map<String, Long> getOrderStatusSummary(Long userId, LocalDate startDate, LocalDate endDate);
}
