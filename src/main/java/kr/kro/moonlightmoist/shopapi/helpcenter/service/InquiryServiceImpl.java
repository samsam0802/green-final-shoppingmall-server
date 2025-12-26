package kr.kro.moonlightmoist.shopapi.helpcenter.service;

import jakarta.transaction.Transactional;
import kr.kro.moonlightmoist.shopapi.helpcenter.domain.Inquiry;
import kr.kro.moonlightmoist.shopapi.helpcenter.dto.*;
import kr.kro.moonlightmoist.shopapi.helpcenter.exception.InquiryNotFoundException;
import kr.kro.moonlightmoist.shopapi.helpcenter.repository.InquiryRepository;
import kr.kro.moonlightmoist.shopapi.user.domain.User;
import kr.kro.moonlightmoist.shopapi.user.exception.UserNotFoundException;
import kr.kro.moonlightmoist.shopapi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class InquiryServiceImpl implements InquiryService{

    private final InquiryRepository inquiryRepository;
    private final UserRepository userRepository;

    @Override
    public InquiryCreateResponse registerInquiry(InquiryCreateRequest createRequest) {
        // 등록 서비스
        User user = userRepository.findByLoginId(createRequest.getLoginId())
                .orElseThrow(UserNotFoundException::new);
//        SELECT * FROM users WHERE login_id = 'user1';
//        조회된 모든 컬럼을 User 엔티티 객체로 매핑
//      1)  loginId는 단순히 검색 조건
//      2)  DB는 해당 조건에 맞는 row 전체를 반환
//      3)  JPA가 그 row의 모든 컬럼을 User 객체의 필드에 자동 매핑

        Inquiry createInquiry = Inquiry.builder()
                .user(user)
                .title(createRequest.getTitle())
                .content(createRequest.getContent())
                .inquiryType(createRequest.getInquiryType())
                .emailAgreement(createRequest.isEmailAgreement())
                .smsAgreement(createRequest.isSmsAgreement())
                .visible(true)
                .answered(false)
                .build();

        inquiryRepository.save(createInquiry);

        return InquiryCreateResponse.builder()
                .success(true)
                .message("문의가 정상적으로 등록 되었습니다.")
                .build();
    }

    @Override
    public InquiryListDTO getInquiryList(String loginId) {

            User user = userRepository.findByLoginId(loginId)
                    .orElseThrow(UserNotFoundException::new);
//        SELECT * FROM inquiry WHERE user_id = ?

            List<Inquiry> inquiries = inquiryRepository.findByUser(user);

            List<InquiryDTO> response = inquiries.stream()
                    .map(inquiry -> InquiryDTO.toInquiryDTO(inquiry)).collect(Collectors.toList());

            return new InquiryListDTO(response, response.size());

    }

    @Override
    public InquiryModifyResponse modifyInquiry(Long InquiryId, InquiryModifyRequest request, String loginId) {

        // loginId로 사용자를 찾고
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UserNotFoundException());

        // 해당 찾은 사용자의 문의를 찾음
        Inquiry inquiry = inquiryRepository.findById(InquiryId)
                .orElseThrow(() -> new InquiryNotFoundException());

        inquiry.updateInquiry(request);

        inquiryRepository.save(inquiry);

        return InquiryModifyResponse.builder()
                .id(inquiry.getId())
                .title(inquiry.getTitle())
                .inquiryContent(inquiry.getContent())
                .inquiryType(inquiry.getInquiryType().name())
                .inquiryTypeName(inquiry.getInquiryType().getTypeName()) // enum 내부 한글명 가져오기
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Override
    public void deleteInquiry(Long id, String loginId) {
        Inquiry inquiry = inquiryRepository.findById(id)
                .orElseThrow(() -> new InquiryNotFoundException("해당 문의가 존재하지 않습니다"));

        inquiryRepository.delete(inquiry);
    }


}
