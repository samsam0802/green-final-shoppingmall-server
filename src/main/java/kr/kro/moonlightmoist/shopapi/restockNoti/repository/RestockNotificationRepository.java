package kr.kro.moonlightmoist.shopapi.restockNoti.repository;

import kr.kro.moonlightmoist.shopapi.restockNoti.domain.RestockNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RestockNotificationRepository extends JpaRepository<RestockNotification, Long> {

    List<RestockNotification> findByProductOptionId(Long productOptionId);

    @Query("SELECT n FROM RestockNotification n " +
            "WHERE n.productOption.id = :optionId " +
            "AND n.notificationStatus = 'WAITING'")
    List<RestockNotification> findByOptionIdAndWaiting(@Param("optionId") Long optionId);

    Optional<RestockNotification> findByUserIdAndProductOptionId(Long userId, Long optionId);
}
