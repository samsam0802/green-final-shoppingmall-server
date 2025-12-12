package kr.kro.moonlightmoist.shopapi.pointHistory.service;

public interface PointHistoryService {
    int getActivePoint(Long userId);
    void earnPoint(Long userId, Long orderId, int pointValue);
    void usePoint(Long userId, Long orderId, int amountToUse);
    void rollbackPoint(Long orderId);
}
