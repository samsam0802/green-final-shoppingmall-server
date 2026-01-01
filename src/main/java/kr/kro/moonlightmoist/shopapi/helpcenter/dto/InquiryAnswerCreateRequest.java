package kr.kro.moonlightmoist.shopapi.helpcenter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InquiryAnswerCreateRequest {
    private String answerContent;
}
