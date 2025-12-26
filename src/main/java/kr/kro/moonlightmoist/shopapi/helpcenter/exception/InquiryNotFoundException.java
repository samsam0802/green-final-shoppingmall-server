package kr.kro.moonlightmoist.shopapi.helpcenter.exception;

import kr.kro.moonlightmoist.shopapi.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class InquiryNotFoundException extends BusinessException {

    public InquiryNotFoundException () {
        super(HttpStatus.NOT_FOUND, "해당 문의를 찾을 수 없습니다.");
    }

    public InquiryNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
