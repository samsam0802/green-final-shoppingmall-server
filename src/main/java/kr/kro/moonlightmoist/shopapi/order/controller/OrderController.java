package kr.kro.moonlightmoist.shopapi.order.controller;

import kr.kro.moonlightmoist.shopapi.order.dto.OrderProductRequestDTO;
import kr.kro.moonlightmoist.shopapi.order.dto.OrderRequestDTO;
import kr.kro.moonlightmoist.shopapi.order.dto.OrderResponseDTO;
import kr.kro.moonlightmoist.shopapi.order.service.OrderService;
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

    @PostMapping("")
    public ResponseEntity<Long> registerOrder(@RequestBody OrderRequestDTO orderRequestDTO, @RequestParam Long userId) {
        log.info("registerOrder 메서드 실행 dto :{}", orderRequestDTO);
        return ResponseEntity.ok(orderService.createOrder(orderRequestDTO, userId));
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
}
