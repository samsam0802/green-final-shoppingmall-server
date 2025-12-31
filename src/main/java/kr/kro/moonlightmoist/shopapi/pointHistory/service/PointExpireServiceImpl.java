package kr.kro.moonlightmoist.shopapi.pointHistory.service;

import kr.kro.moonlightmoist.shopapi.pointHistory.domain.PointHistory;
import kr.kro.moonlightmoist.shopapi.pointHistory.repository.PointHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PointExpireServiceImpl implements PointExpireService {

    private final PointHistoryRepository pointHistoryRepository;

    @Override
    public List<PointHistory> getExpiredPoints() {
        List<PointHistory> expiredPoints = pointHistoryRepository.findExpiredPoints(LocalDateTime.now());

        return expiredPoints;
    }

    @Override
    @Transactional
    public int expirePoints() {
        int cnt = pointHistoryRepository.updatePointStatusToExpired(LocalDateTime.now());
        if (cnt == 0) {
            log.info("만료 처리할 포인트가 없습니다.");
        }
        return cnt;
    }

}
