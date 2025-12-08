package kr.kro.moonlightmoist.shopapi.pointHistory.service;

public interface PointHistoryService {
    int getActivePoint(Long userId);
    void earnPoint(Long userId, int pointValue);
    void usePoint(Long userId, int amountToUse);
}
