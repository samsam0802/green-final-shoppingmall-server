package kr.kro.moonlightmoist.shopapi.product.exception;

public class InSufficientStockException extends RuntimeException {
    public InSufficientStockException(int requestedQuantity, int currentStock) {
        super(String.format(
                "재고가 부족합니다. 요청수량: %d, 현재재고: %d",
                requestedQuantity, currentStock
        ));
    }
}
