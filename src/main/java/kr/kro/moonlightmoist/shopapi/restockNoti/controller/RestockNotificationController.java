package kr.kro.moonlightmoist.shopapi.restockNoti.controller;

import kr.kro.moonlightmoist.shopapi.restockNoti.dto.RestockNotiReq;
import kr.kro.moonlightmoist.shopapi.restockNoti.repository.RestockNotificationRepository;
import kr.kro.moonlightmoist.shopapi.restockNoti.service.RestockNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/restock-notification")
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*",
        methods = {RequestMethod.GET,
                RequestMethod.POST,
                RequestMethod.PUT,
                RequestMethod.DELETE,
                RequestMethod.OPTIONS})
@RequiredArgsConstructor
public class RestockNotificationController {

    private final RestockNotificationService restockNotificationService;

    @PostMapping("")
    public ResponseEntity<Long> applyRestockAlarm(@RequestBody RestockNotiReq req) {
        log.info("userId : {}, optionId : {}", req.getUserId(), req.getOptionId());
        Long id = restockNotificationService.applyRestockNotification(req.getUserId(), req.getOptionId());
        return ResponseEntity.ok(id);
    }
}
