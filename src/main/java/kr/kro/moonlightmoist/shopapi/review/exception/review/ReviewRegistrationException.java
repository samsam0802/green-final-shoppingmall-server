package kr.kro.moonlightmoist.shopapi.review.exception.review;

import kr.kro.moonlightmoist.shopapi.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class ReviewRegistrationException extends BusinessException {
    public ReviewRegistrationException() {
        super(HttpStatus.BAD_REQUEST, "리뷰 등록 처리 중 오류가 생겼습니다.");
    }

    public ReviewRegistrationException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
