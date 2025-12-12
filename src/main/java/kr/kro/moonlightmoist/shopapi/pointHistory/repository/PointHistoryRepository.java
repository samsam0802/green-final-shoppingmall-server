package kr.kro.moonlightmoist.shopapi.pointHistory.repository;

import kr.kro.moonlightmoist.shopapi.pointHistory.domain.PointHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {

    @Query("SELECT p FROM PointHistory p " +
            "WHERE p.user.id = :userId " +
            "AND p.pointStatus = 'EARNED' " +
            "AND p.remainingPoint > 0 " +
            "AND p.expiredAt > :now " +
            "ORDER BY p.expiredAt ASC"
    )
    List<PointHistory> findAllActivePoints(
            @Param("userId") Long userId,
            @Param("now") LocalDateTime now
    );

    // 누적 포인트(사용안한것, 사용한것 다 포함) 조회 : 등급 업그레이드를 위한 쿼리
    @Query("SELECT COALESCE(SUM(p.pointValue), 0) FROM PointHistory p " +
            "WHERE p.user.id = :userId " +
            "AND p.pointStatus = 'EARNED'"
    )
    Long findCumulativePoints(@Param("userId") Long userId);

    // 만료 대상 조회 : pointStatus 가 'EARNED' 이지만 만료기간이 지난 포인트히스토리 조회
    @Query("SELECT p FROM PointHistory p " +
            "WHERE p.pointStatus = 'EARNED' " +
            "AND p.expiredAt <= :now " +
            "AND p.deleted = false"
    )
    List<PointHistory> findExpiredPoints(@Param("now") LocalDateTime now);

    // 만료기간 지난 포인트 일괄 만료 처리 (업데이트 쿼리 1개 : 벌크 업데이트)
    @Modifying
    @Query("UPDATE PointHistory p " +
            "SET p.pointStatus = 'EXPIRED' " +
            "WHERE p.pointStatus = 'EARNED' " +
            "AND p.expiredAt < :now " +
            "AND p.deleted = false")
    int updatePointStatusToExpired(@Param("now") LocalDateTime now);

    // 주문 id 로 히스토리 찾기
    List<PointHistory> findByOrderId(Long orderId);
}
