package kr.kro.moonlightmoist.shopapi.pointHistory.controller;

import kr.kro.moonlightmoist.shopapi.pointHistory.dto.PointEarnReq;
import kr.kro.moonlightmoist.shopapi.pointHistory.dto.PointUseReq;
import kr.kro.moonlightmoist.shopapi.pointHistory.repository.PointHistoryRepository;
import kr.kro.moonlightmoist.shopapi.pointHistory.service.PointHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/point")
@Slf4j
@RequiredArgsConstructor
public class PointHistoryController {

    private final PointHistoryService pointHistoryService;

    @GetMapping("/{userId}")
    public ResponseEntity<Integer> getActivePoints(@PathVariable("userId") Long userId) {
        int activePoint = pointHistoryService.getActivePoint(userId);

        return ResponseEntity.ok(activePoint);
    }

    @PostMapping("/earn")
    public ResponseEntity<String> earnPoint(@RequestBody PointEarnReq dto) {
        pointHistoryService.earnPoint(dto.getUserId(), dto.getOrderId(), dto.getPointValue());
        return ResponseEntity.ok("ok");
    }

    @PostMapping("/use")
    public ResponseEntity<String> usePoint(@RequestBody PointUseReq dto) {
        pointHistoryService.usePoint(dto.getUserId(), dto.getOrderId(), dto.getAmountToUse());
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/rollback/{orderId}")
    public ResponseEntity<String> rollbackPoint(@PathVariable(name = "orderId") Long orderId) {
        pointHistoryService.rollbackPoint(orderId);

        return ResponseEntity.ok("ok");
    }

}
