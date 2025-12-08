package kr.kro.moonlightmoist.shopapi.pointHistory.service;

import kr.kro.moonlightmoist.shopapi.pointHistory.domain.PointHistory;
import kr.kro.moonlightmoist.shopapi.pointHistory.domain.PointStatus;
import kr.kro.moonlightmoist.shopapi.pointHistory.repository.PointHistoryRepository;
import kr.kro.moonlightmoist.shopapi.user.domain.User;
import kr.kro.moonlightmoist.shopapi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PointHistoryServiceImpl implements PointHistoryService{

    private final UserRepository userRepository;
    private final PointHistoryRepository pointHistoryRepository;


    @Override
    public int getActivePoint(Long userId) {
        List<PointHistory> allActivePoints = pointHistoryRepository.findAllActivePoints(userId, LocalDateTime.now());
        int sum = allActivePoints.stream()
                .mapToInt(p -> p.getRemainingPoint())
                .sum();
        return sum;
    }

    @Override
    public void earnPoint(Long userId, int pointValue) {
        User user = userRepository.findById(userId).get();

        PointHistory pointHistory = PointHistory.builder()
                .user(user)
                .pointStatus(PointStatus.EARNED)
                .pointValue(pointValue)
                .remainingPoint(pointValue)
                .expiredAt(LocalDateTime.now().plusYears(1))
                .build();
        pointHistoryRepository.save(pointHistory);
    }

    @Override
    @Transactional
    public void usePoint(Long userId, int amountToUse) {
        // 유효기간 얼마 안남은 순서로 가져옴
        List<PointHistory> allActivePoints = pointHistoryRepository.findAllActivePoints(userId, LocalDateTime.now());

        int totalBalance = allActivePoints.stream().mapToInt(p -> p.getRemainingPoint()).sum();
        if (totalBalance < amountToUse) {
            throw new RuntimeException("잔여 포인트가 사용하려는 포인트보다 적습니다.");
        }

        int remainingAmountToUse = amountToUse;

        for (PointHistory history : allActivePoints) {
            int availablePoint = history.getRemainingPoint();

            if (availablePoint >= remainingAmountToUse) {
                // 이 내역 하나로 충분할 때
                history.setRemainingPoint(availablePoint - remainingAmountToUse);
                remainingAmountToUse = 0;
                break;
            } else {
                // 이 내역을 다 써도 부족할 때 (부분 차감)
                history.setRemainingPoint(0);
                remainingAmountToUse -= availablePoint;
            }
        }

        User user = userRepository.findById(userId).get();
        // 사용 history 를 남겨야함
        PointHistory usageHistory = PointHistory.builder()
                .user(user)
                .pointStatus(PointStatus.EARNED)
                .pointValue(-amountToUse)
                .remainingPoint(0)
                .build();
        pointHistoryRepository.save(usageHistory);
    }
}
