package kr.kro.moonlightmoist.shopapi.helpcenter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class InquiryAnswerResponse {
    private Long inquiryId;
    private String answerContent;
    private LocalDateTime answerCreatedAt;
    private boolean answered;
    private String message;
}
