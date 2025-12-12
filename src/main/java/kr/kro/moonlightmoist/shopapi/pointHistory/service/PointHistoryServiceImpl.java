package kr.kro.moonlightmoist.shopapi.pointHistory.service;

import jakarta.persistence.EntityNotFoundException;
import kr.kro.moonlightmoist.shopapi.order.domain.Order;
import kr.kro.moonlightmoist.shopapi.order.repository.OrderRepository;
import kr.kro.moonlightmoist.shopapi.pointHistory.domain.PointHistory;
import kr.kro.moonlightmoist.shopapi.pointHistory.domain.PointStatus;
import kr.kro.moonlightmoist.shopapi.pointHistory.domain.PointUsageDetail;
import kr.kro.moonlightmoist.shopapi.pointHistory.repository.PointHistoryRepository;
import kr.kro.moonlightmoist.shopapi.pointHistory.repository.PointUsageDetailRepository;
import kr.kro.moonlightmoist.shopapi.user.domain.User;
import kr.kro.moonlightmoist.shopapi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class PointHistoryServiceImpl implements PointHistoryService{

    private final UserRepository userRepository;
    private final PointHistoryRepository pointHistoryRepository;
    private final PointUsageDetailRepository pointUsageDetailRepository;
    private final OrderRepository orderRepository;


    @Override
    public int getActivePoint(Long userId) {
        List<PointHistory> allActivePoints = pointHistoryRepository.findAllActivePoints(userId, LocalDateTime.now());
        int sum = allActivePoints.stream()
                .mapToInt(p -> p.getRemainingPoint())
                .sum();
        return sum;
    }

    @Override
    @Transactional
    public void earnPoint(Long userId, Long orderId, int pointValue) {
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);

        PointHistory pointHistory = PointHistory.builder()
                .user(user)
                .order(order)
                .pointStatus(PointStatus.EARNED)
                .pointValue(pointValue)
                .remainingPoint(pointValue)
                .expiredAt(LocalDateTime.now().plusYears(1))
                .build();
        pointHistoryRepository.save(pointHistory);
    }

    @Override
    @Transactional
    public void usePoint(Long userId, Long orderId, int amountToUse) {
        // 유효기간 얼마 안남은 순서로 가져옴
        List<PointHistory> allActivePoints = pointHistoryRepository.findAllActivePoints(userId, LocalDateTime.now());
        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);

        int totalBalance = allActivePoints.stream().mapToInt(p -> p.getRemainingPoint()).sum();
        if (totalBalance < amountToUse) {
            throw new RuntimeException("잔여 포인트가 사용하려는 포인트보다 적습니다.");
        }

        int remainingAmountToUse = amountToUse;
        List<PointUsageDetail> usageDetails = new ArrayList<>();

        for (PointHistory history : allActivePoints ) {
            if (remainingAmountToUse == 0) {
                break;
            }

            int availablePoint = history.getRemainingPoint();
            int usedPointInHistory = 0;

            if (availablePoint >= remainingAmountToUse) {
                usedPointInHistory = remainingAmountToUse;
                // 이 내역 하나로 충분할 때
                history.setRemainingPoint(availablePoint - remainingAmountToUse);
                remainingAmountToUse = 0;
            } else {
                usedPointInHistory = availablePoint;
                // 이 내역을 다 써도 부족할 때 (부분 차감)
                history.setRemainingPoint(0);
                remainingAmountToUse -= availablePoint;
            }

            if (usedPointInHistory > 0) {
                usageDetails.add(PointUsageDetail.builder()
                                .earnedPointHistory(history)
                                .usedAmount(usedPointInHistory)
                        .build()
                );
            }
        }

        User user = userRepository.findById(userId).get();
        // 사용 history 를 남겨야함
        PointHistory usedHistory = PointHistory.builder()
                .user(user)
                .order(order)
                .pointStatus(PointStatus.USED)
                .pointValue(-amountToUse)
                .remainingPoint(0)
                .build();
        pointHistoryRepository.save(usedHistory);

        // PointUsageDetail에 usedPointHistory 기록 후 저장
        usageDetails.forEach(detail -> {
            detail.setUsedPointHistory(usedHistory);
            pointUsageDetailRepository.save(detail);
        });
    }

    @Override
    @Transactional
    public void rollbackPoint(Long orderId) {

        PointHistory usedHistory = pointHistoryRepository.findByOrderId(orderId)
                .stream().filter(h -> h.getPointStatus().equals(PointStatus.USED))
                .findFirst().orElseThrow(EntityNotFoundException::new);

        List<PointUsageDetail> pointUsages = pointUsageDetailRepository.findByUsedPointHistoryIdWithEarned(usedHistory.getId());

        List<PointHistory> earnedHistories = pointUsages.stream()
                .map(u -> u.getEarnedPointHistory()).toList();

        // 포인트 복구
        for (PointUsageDetail pointUsage : pointUsages) {
            PointHistory earnedHistory = pointUsage.getEarnedPointHistory();

            earnedHistory.plusRemainingPoint(pointUsage.getUsedAmount());
        }

        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
        User user = order.getUser();

        // CANCELLED 포인트 히스토리 생성
        PointHistory cancelledHistory = PointHistory.builder()
                .user(user)
                .order(order)
                .pointStatus(PointStatus.CANCELLED)
                .pointValue(order.getUsedPoints())
                .build();

        pointHistoryRepository.save(cancelledHistory);
    }
}
