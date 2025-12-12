package kr.kro.moonlightmoist.shopapi.order.controller;

import kr.kro.moonlightmoist.shopapi.order.dto.OrderResBySearch;
import kr.kro.moonlightmoist.shopapi.order.dto.OrderRequestDTO;
import kr.kro.moonlightmoist.shopapi.order.dto.OrderResponseDTO;
import kr.kro.moonlightmoist.shopapi.order.dto.OrderSearchCondition;
import kr.kro.moonlightmoist.shopapi.order.service.OrderCouponService;
import kr.kro.moonlightmoist.shopapi.order.service.OrderService;
import kr.kro.moonlightmoist.shopapi.pointHistory.service.PointHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*",
            methods = {RequestMethod.POST,
                    RequestMethod.PUT,
                    RequestMethod.GET,
                    RequestMethod.DELETE,
                    RequestMethod.OPTIONS
            })
@RequestMapping("/api/order")
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

    @GetMapping("/list")
    public List<OrderResponseDTO> getOrderList(@RequestParam Long userId) {
        return orderService.getOrderList(userId);
    }

    @DeleteMapping("")
    public ResponseEntity<String> deleteOneOrder(@RequestParam Long orderId) {
        log.info("deleteOneOrder 메서드 실행 orderId:{}", orderId);
        orderService.deleteOneOrder(orderId);
        return ResponseEntity.ok("삭제 성공");
    }

    @PostMapping("/search")
    public ResponseEntity<List<OrderResBySearch>> searchOrdersByCondition(@RequestBody OrderSearchCondition condition) {
        List<OrderResBySearch> orderResBySearches = orderService.searchOrdersByCondition(condition);

        return ResponseEntity.ok(orderResBySearches);
    }

    @PutMapping("/confirm/{orderId}")
    public ResponseEntity<String> confirmOrder(@PathVariable Long orderId) {
        orderService.comfirmOrder(orderId);
        return ResponseEntity.ok("ok");
    }
}
