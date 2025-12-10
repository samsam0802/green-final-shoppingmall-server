package kr.kro.moonlightmoist.shopapi.restockNoti.repository;

import kr.kro.moonlightmoist.shopapi.restockNoti.domain.RestockNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestockNotificationRepository extends JpaRepository<RestockNotification, Long> {

    List<RestockNotification> findByProductOptionId(Long productOptionId);
}
