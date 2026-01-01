package kr.kro.moonlightmoist.shopapi.helpcenter.exception;

import kr.kro.moonlightmoist.shopapi.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class AlreadyAnsweredException extends BusinessException {

    public AlreadyAnsweredException() {
        super(HttpStatus.BAD_REQUEST, "이미 답변이 등록된 문의 입니다.");
    }

    public AlreadyAnsweredException(String message) {
        super(HttpStatus.BAD_REQUEST,message);
    }
}
