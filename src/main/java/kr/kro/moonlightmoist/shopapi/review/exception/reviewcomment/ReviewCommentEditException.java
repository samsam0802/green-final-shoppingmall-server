package kr.kro.moonlightmoist.shopapi.review.exception.reviewcomment;

import kr.kro.moonlightmoist.shopapi.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class ReviewCommentEditException extends BusinessException {
    public ReviewCommentEditException() {
        super(HttpStatus.BAD_REQUEST, "댓글 수정 처리 중 오류가 생겼습니다.");
    }

    public ReviewCommentEditException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
