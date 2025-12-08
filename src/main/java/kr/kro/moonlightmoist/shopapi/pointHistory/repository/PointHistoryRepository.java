package kr.kro.moonlightmoist.shopapi.pointHistory.repository;

import kr.kro.moonlightmoist.shopapi.pointHistory.domain.PointHistory;
import org.springframework.data.jpa.repository.JpaRepository;
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

    // 누적 포인트
    @Query("SELECT COALESCE(SUM(p.pointValue), 0) FROM PointHistory p " +
            "WHERE p.user.id = :userId " +
            "AND p.pointStatus = 'EARNED'"
    )
    Long findCumulativePoints(@Param("userId") Long userId);

}
