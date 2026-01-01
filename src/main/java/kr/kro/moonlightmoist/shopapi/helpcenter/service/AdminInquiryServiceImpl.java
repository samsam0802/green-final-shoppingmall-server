package kr.kro.moonlightmoist.shopapi.helpcenter.service;

import kr.kro.moonlightmoist.shopapi.helpcenter.domain.Inquiry;
import kr.kro.moonlightmoist.shopapi.helpcenter.dto.*;
import kr.kro.moonlightmoist.shopapi.helpcenter.exception.AlreadyAnsweredException;
import kr.kro.moonlightmoist.shopapi.helpcenter.exception.AnswerNotFoundException;
import kr.kro.moonlightmoist.shopapi.helpcenter.exception.InquiryNotFoundException;
import kr.kro.moonlightmoist.shopapi.helpcenter.repository.InquiryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminInquiryServiceImpl implements AdminInquiryService {

    private final InquiryRepository inquiryRepository;


    @Override
    public List<InquiryDTO> readInquiryList() {
        return inquiryRepository.findAll().stream()
                .map( inquiry -> InquiryDTO.builder()
                        .inquiryType(inquiry.getInquiryType())
                        .title(inquiry.getTitle())
                        .content(inquiry.getContent())
                        .answerContent(inquiry.getContent())
                        .answered(inquiry.isAnswered())
                        .emailAgreement(inquiry.isEmailAgreement())
                        .smsAgreement(inquiry.isSmsAgreement())
                        .createdAt(inquiry.getCreatedAt())
                        .updatedAt(inquiry.getUpdatedAt())
                        .answerCreatedAt(inquiry.getAnswerCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public AdminInquiryListDTO getAdminInquiries(String status, String type, String keyword) {
        log.info("문의 목록 조회 - status: {}, type: {}, keyword: {}", status, type, keyword);

        List<Inquiry> allInquiries = inquiryRepository.findAll(); // DB에서 문의목록 전체 가져옴

        List<Inquiry> filteredInquiries = allInquiries.stream() // 목록리스트 stream 펼침
                .filter(inquiry -> {

                    // 조건문 필터
                    // 답변 상태 필터 로직
                    if (status != null && !status.equals("ALL")) {
                        boolean isAnswered = status.equals("ANSWERED");
                        if (inquiry.isAnswered() != isAnswered) {
                            return false; // 조건 안 맞으면 제외
                        }
                    }

                    // 문의 유형 필터 로직
                    if (type != null && !type.equals("전체")) {
                        String inquiryTypeName = inquiry.getInquiryType().getTypeName();
                        if (!inquiryTypeName.equals(type)) {
                            return false; // 조건 안 맞으면 제외
                        }
                    }

                    // 키워드 필터 검색 (제목, 내용, 작성자 이름, ID)
                    if (keyword != null && !keyword.trim().isEmpty()) {
                        String searchKeyword = keyword.trim().toLowerCase();

                        // 소문자 변환
                        String title = inquiry.getTitle().toLowerCase();
                        String content = inquiry.getContent().toLowerCase();
                        String userName = inquiry.getUser().getName().toLowerCase();
                        String loginId = inquiry.getUser().getLoginId().toLowerCase();

                        // 하나라도 포함되면 true
                        boolean isMatch = title.contains(searchKeyword) ||
                                content.contains(searchKeyword) ||
                                userName.contains(searchKeyword) ||
                                loginId.contains(searchKeyword);

                        return isMatch;
                    }

                    return true; // 모든 필터 통과
                })
                .collect(Collectors.toList()); // 리스트생성

        List<AdminInquiryDTO> dtoList = filteredInquiries.stream()
                .map(inquiry -> AdminInquiryDTO.toEntity(inquiry)) // 변환
                .collect(Collectors.toList()); // 리스트 생성

        log.info("필터링 결과: 전체 {}건 → {}건", allInquiries.size(), dtoList.size()); // 체크

        return new AdminInquiryListDTO(dtoList, dtoList.size()); // 변환된 리스트 반환 (필터링된것)
    }


    @Override
    public InquiryAnswerResponse createAnswer(Long inquiryId, InquiryAnswerCreateRequest request) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(InquiryNotFoundException::new);

        if (inquiry.isAnswered()) {
            throw new AlreadyAnsweredException();
        }

        inquiry.setAnswerContent(request.getAnswerContent());
        inquiry.setAnswered(true);
        inquiry.setAnswerCreatedAt(LocalDateTime.now());
        inquiryRepository.save(inquiry);

        return InquiryAnswerResponse.builder()
                .inquiryId(inquiry.getId())
                .answerContent(inquiry.getAnswerContent())
                .answerCreatedAt(inquiry.getCreatedAt())
                .answered(true)
                .message("답변이 성공적으로 등록 되었습니다.")
                .build();
    }

    @Override
    public InquiryAnswerResponse updateAnswer(Long inquiryId, InquiryAnswerCreateRequest request) {
        log.info("답변 수정 시작 - 문의 ID: {}", inquiryId);

        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new InquiryNotFoundException("문의를 찾을 수 없습니다."));

        // 답변여부 확인
        if (!inquiry.isAnswered()) {
            throw new AnswerNotFoundException();
        }

        inquiry.setAnswerContent(request.getAnswerContent());
        inquiry.setAnswerCreatedAt(LocalDateTime.now());
        inquiryRepository.save(inquiry);

        log.info("답변 수정 완료 - 문의 ID: {}", inquiryId);


        return InquiryAnswerResponse.builder()
                .inquiryId(inquiry.getId())
                .answerContent(inquiry.getAnswerContent())
                .answerCreatedAt(inquiry.getAnswerCreatedAt())
                .answered(true)
                .message("답변이 수정되었습니다.")
                .build();
    }


    @Override
    public void deleteInquiry(Long inquiryId) {
        log.info("문의 삭제 시작 - 문의 ID: {}", inquiryId);

        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new InquiryNotFoundException("문의를 찾을 수 없습니다."));

        inquiryRepository.delete(inquiry);

        log.info("문의 삭제 완료 - 문의 ID: {}", inquiryId);
    }

}
