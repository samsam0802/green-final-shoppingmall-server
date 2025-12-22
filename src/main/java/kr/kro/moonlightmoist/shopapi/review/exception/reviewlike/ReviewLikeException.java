package kr.kro.moonlightmoist.shopapi.review.exception.reviewlike;

import kr.kro.moonlightmoist.shopapi.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class ReviewLikeException extends BusinessException {
    public ReviewLikeException() {
        super(HttpStatus.BAD_REQUEST, "도움이 돼요 처리 중 오류가 발생했습니다.");
    }

    public ReviewLikeException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
