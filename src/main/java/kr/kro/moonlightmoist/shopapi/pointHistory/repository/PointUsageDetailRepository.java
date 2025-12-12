package kr.kro.moonlightmoist.shopapi.pointHistory.repository;

import kr.kro.moonlightmoist.shopapi.pointHistory.domain.PointUsageDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PointUsageDetailRepository extends JpaRepository<PointUsageDetail, Long> {
    @Query("SELECT d FROM PointUsageDetail d " +
            "JOIN FETCH d.earnedPointHistory " +
            "WHERE d.usedPointHistory.id = :usedHistoryId")
    List<PointUsageDetail> findByUsedPointHistoryIdWithEarned(@Param("usedHistoryId") Long usedHistoryId);
}
