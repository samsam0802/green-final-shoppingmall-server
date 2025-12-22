package kr.kro.moonlightmoist.shopapi.review.exception.reviewcomment;

import kr.kro.moonlightmoist.shopapi.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class ReviewCommentDeletionException extends BusinessException {
    public ReviewCommentDeletionException() {
        super(HttpStatus.BAD_REQUEST, "댓글 삭제 처리 중 오류가 생겼습니다.");
    }

    public ReviewCommentDeletionException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
