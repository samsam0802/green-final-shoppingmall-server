package kr.kro.moonlightmoist.shopapi.review.exception.review;

import kr.kro.moonlightmoist.shopapi.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class ReviewEditException extends BusinessException {
    public ReviewEditException() {
        super(HttpStatus.BAD_REQUEST, "리뷰 수정 처리 중 오류가 생겼습니다.");
    }

    public ReviewEditException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
