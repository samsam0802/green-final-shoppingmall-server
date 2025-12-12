package kr.kro.moonlightmoist.shopapi.restockNoti.service;

import jakarta.persistence.EntityNotFoundException;
import kr.kro.moonlightmoist.shopapi.product.domain.ProductOption;
import kr.kro.moonlightmoist.shopapi.product.repository.ProductOptionRepository;
import kr.kro.moonlightmoist.shopapi.restockNoti.domain.NotificationStatus;
import kr.kro.moonlightmoist.shopapi.restockNoti.domain.NotificationType;
import kr.kro.moonlightmoist.shopapi.restockNoti.domain.RestockNotification;
import kr.kro.moonlightmoist.shopapi.restockNoti.repository.RestockNotificationRepository;
import kr.kro.moonlightmoist.shopapi.user.domain.User;
import kr.kro.moonlightmoist.shopapi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestockNotificationServiceImpl implements RestockNotificationService{

    private final RestockNotificationRepository restockNotificationRepository;
    private final UserRepository userRepository;
    private final ProductOptionRepository productOptionRepository;

    @Override
    public Long applyRestockNotification(Long userId, Long optionId) {

        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        ProductOption option = productOptionRepository.findById(optionId).orElseThrow(EntityNotFoundException::new);

        if (restockNotificationRepository.findByUserIdAndProductOptionId(userId, optionId).isPresent()) {
            return Long.valueOf(0);
        }


        RestockNotification restockNotification = RestockNotification.builder()
                .user(user)
                .productOption(option)
                .notificationType(NotificationType.SMS)
                .notificationStatus(NotificationStatus.WAITING)
                .build();

        return restockNotificationRepository.save(restockNotification).getId();
    }

}
