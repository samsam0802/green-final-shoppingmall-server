package kr.kro.moonlightmoist.shopapi.helpcenter.dto;

import kr.kro.moonlightmoist.shopapi.helpcenter.domain.Inquiry;
import kr.kro.moonlightmoist.shopapi.helpcenter.domain.InquiryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminInquiryDTO {

    private Long id;
    private String title;
    private String content;
    private InquiryType inquiryType;
    private boolean answered;
    private String answerContent;
    private LocalDateTime createdAt;
    private LocalDateTime answerCreatedAt;

    private String userName;
    private String loginId;

    private boolean emailAgreement;
    private boolean smsAgreement;

    public static AdminInquiryDTO toEntity(Inquiry inquiry) {
        return AdminInquiryDTO.builder()
                .id(inquiry.getId())
                .title(inquiry.getTitle())
                .content(inquiry.getContent())
                .inquiryType(inquiry.getInquiryType())
                .answered(inquiry.isAnswered())
                .answerContent(inquiry.getAnswerContent())
                .createdAt(inquiry.getCreatedAt())
                .answerCreatedAt(inquiry.getAnswerCreatedAt())
                .userName(inquiry.getUser().getName())
                .loginId(inquiry.getUser().getLoginId())
                .emailAgreement(inquiry.isEmailAgreement())
                .smsAgreement(inquiry.isSmsAgreement())
                .build();
    }

}
