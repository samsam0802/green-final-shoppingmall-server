package kr.kro.moonlightmoist.shopapi.helpcenter.exception;

import kr.kro.moonlightmoist.shopapi.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class AnswerNotFoundException extends BusinessException {
    public AnswerNotFoundException(){
        super(HttpStatus.NOT_FOUND, "등록된 답변이 없습니다");

    }

    public AnswerNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
