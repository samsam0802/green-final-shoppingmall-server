package kr.kro.moonlightmoist.shopapi.review.exception.review;

import kr.kro.moonlightmoist.shopapi.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class ReviewDeletionException extends BusinessException {
    public ReviewDeletionException() {
        super(HttpStatus.BAD_REQUEST, "리뷰를 삭제 처리 중 오류가 생겼습니다.");
    }

    public ReviewDeletionException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
