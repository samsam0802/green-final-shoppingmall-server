package kr.kro.moonlightmoist.shopapi.review.exception.reviewcomment;

import kr.kro.moonlightmoist.shopapi.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class ReviewCommentRegistrationException extends BusinessException {
    public ReviewCommentRegistrationException() {
        super(HttpStatus.BAD_REQUEST, "댓글 등록 처리 중 오류가 생겼습니다.");
    }

    public ReviewCommentRegistrationException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
