package kr.kro.moonlightmoist.shopapi.order.controller;

import kr.kro.moonlightmoist.shopapi.order.dto.*;
import kr.kro.moonlightmoist.shopapi.order.service.OrderCouponService;
import kr.kro.moonlightmoist.shopapi.order.service.OrderService;
import kr.kro.moonlightmoist.shopapi.pointHistory.service.PointHistoryService;
import kr.kro.moonlightmoist.shopapi.review.dto.PageRequestDTO;
import kr.kro.moonlightmoist.shopapi.review.dto.PageResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class OrderController {
    private final OrderService orderService;
    private final OrderCouponService orderCouponService;
    private final PointHistoryService pointHistoryService;

    @PostMapping("")
    public ResponseEntity<Long> registerOrder(@RequestBody OrderRequestDTO orderRequestDTO, @RequestParam Long userId) {
        log.info("registerOrder 메서드 실행 dto :{}", orderRequestDTO);
        Long orderId = orderService.createOrder(orderRequestDTO, userId);
        // 사용한 포인트 차감
        pointHistoryService.usePoint(userId, orderId, orderRequestDTO.getUsedPoints());

        return ResponseEntity.ok(orderId);
    }

    @GetMapping("")
    public ResponseEntity<OrderResponseDTO> getOneOrder(@RequestParam Long orderId) {
        log.info("getOneOrder 메서드 실행 orderId:{}", orderId);
        return ResponseEntity.ok(orderService.getOneOrder(orderId));
    }

//    @GetMapping("/list/{userId}")
//    public ResponseEntity<PageResponseDTO<OrderResponseDTO>> getOrderList(
//            @PathVariable Long userId,
//            @RequestParam(defaultValue = "latest") String sort, // 기본 최신순
//            @RequestParam(defaultValue = "1") int page,
//            @RequestParam(defaultValue = "10") int size
//    ) {
//        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
//                .page(page)
//                .size(size)
//                .build();
//        return ResponseEntity.ok(orderService.getOrderList(userId, sort, pageRequestDTO));
//    }

    @DeleteMapping("")
    public ResponseEntity<String> deleteOneOrder(@RequestParam Long orderId) {
        log.info("deleteOneOrder 메서드 실행 orderId:{}", orderId);
        orderService.deleteOneOrder(orderId); // 주문 삭제(주문 상품 삭제, 주문 쿠폰 삭제, 유저 쿠폰 회복, 주문 삭제)

        pointHistoryService.rollbackPoint(orderId); // 포인트 롤백
        return ResponseEntity.ok("삭제 성공");
    }

    @PostMapping("/search")
    public ResponseEntity<PageResponseDTO<OrderResBySearch>> searchOrdersByCondition(
            @RequestBody OrderSearchCondition condition,
            @RequestParam(defaultValue = "latest") String sort,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(page)
                .size(size)
                .build();

        return ResponseEntity.ok(orderService.searchOrdersByCondition(condition, sort, pageRequestDTO));
    }

    @PutMapping("/confirm/{orderId}")
    public ResponseEntity<String> confirmOrder(@PathVariable Long orderId) {
        orderService.comfirmOrder(orderId);
        return ResponseEntity.ok("ok");
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<String> changeOrderProductStatus(@PathVariable Long orderId, @RequestBody OrderStatusRequest request) {
//        log.info("changeOrderProductStatus => orderId:{}, status:{}, reason:{}", orderId, request.getStatus(), request.getReason());
        orderService.changeOrderProductStatus(orderId, request.getStatus(), request.getReason());
        return ResponseEntity.ok("주문 상태 변경 완료");
    }

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Long>> getOrderStatusSummary(
            @RequestParam Long userId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
            ) {
        return ResponseEntity.ok(orderService.getOrderStatusSummary(userId, startDate, endDate));
    }

    @PutMapping("/complete")
    public ResponseEntity<String> completeOrder(@RequestBody Map<String,String> request) {
        String impUid = request.get("imp_uid");
        String merchantUid = request.get("merchant_uid");
        orderService.completeOrder(merchantUid, impUid);
        return ResponseEntity.ok("주문의 상태를 결제완료로 변경하였습니다.");
    }
}
