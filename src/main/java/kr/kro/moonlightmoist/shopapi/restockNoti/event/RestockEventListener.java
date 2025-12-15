package kr.kro.moonlightmoist.shopapi.restockNoti.event;

import jakarta.persistence.EntityNotFoundException;
import kr.kro.moonlightmoist.shopapi.notification.service.NotificationService;
import kr.kro.moonlightmoist.shopapi.product.domain.ProductOption;
import kr.kro.moonlightmoist.shopapi.product.repository.ProductOptionRepository;
import kr.kro.moonlightmoist.shopapi.restockNoti.domain.NotificationStatus;
import kr.kro.moonlightmoist.shopapi.restockNoti.domain.RestockNotification;
import kr.kro.moonlightmoist.shopapi.restockNoti.repository.RestockNotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Slf4j
public class RestockEventListener {
    private final ProductOptionRepository productOptionRepository;
    private final RestockNotificationRepository restockNotificationRepository;
    private final NotificationService notificationService;

    @EventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Async
    public void handleRestockEvent(RestockEvent event) {
        try {
            // 재입고 공지 테이블에서 찾기
            List<RestockNotification> notis = restockNotificationRepository.findByOptionIdAndWaiting(event.getOptionId());
            ProductOption option = productOptionRepository.findById(event.getOptionId())
                    .orElseThrow(() -> new EntityNotFoundException());

            for (RestockNotification noti : notis) {
                processNotification(noti, option);
            }

        } catch (Exception e) {
            log.error("재입고 알림 발송 실패 : optionId={}", event.getOptionId());
        }
    }

    private void processNotification(RestockNotification noti, ProductOption option) {
        // 메시지 생성 로직 및 발송 로직
        String message = String.format(
                "달빛나라 촉촉마을 상품 재입고 알림!\n" +
                        "[%s]상품의 옵션 '%s' 이 재입고 되었습니다! ",
                option.getProduct().getBasicInfo().getProductName(),
                option.getOptionName()
        );

        CompletableFuture.runAsync(() -> {
           try {
               notificationService.sendSmsMessage(noti.getUser().getPhoneNumber(), message);
               // 성공
               noti.changeStatus(NotificationStatus.SENT);
               // 성공 로그 저장

           } catch (Exception e) {
               // 발송 실패시 로그 저장
               log.info("발송 실패 : {}", e.getMessage());
           }
        }
        // 사용자 정의 asyncExecutor
//        , asyncExecutor
        );

    }
}
