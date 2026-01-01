package kr.kro.moonlightmoist.shopapi.helpcenter.controller;

import kr.kro.moonlightmoist.shopapi.helpcenter.dto.AdminInquiryListDTO;
import kr.kro.moonlightmoist.shopapi.helpcenter.dto.InquiryAnswerCreateRequest;
import kr.kro.moonlightmoist.shopapi.helpcenter.dto.InquiryAnswerResponse;
import kr.kro.moonlightmoist.shopapi.helpcenter.dto.InquiryListDTO;
import kr.kro.moonlightmoist.shopapi.helpcenter.service.AdminInquiryService;
import kr.kro.moonlightmoist.shopapi.helpcenter.service.InquiryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("api/admin/posts")
@RequiredArgsConstructor
public class AdminInquiryController {

    private final AdminInquiryService adminInquiryService;

    // 문의 목록 조회
    @GetMapping("/inquiries")
    public ResponseEntity<AdminInquiryListDTO> getAllInquiries (
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String keyword
    ) {
        AdminInquiryListDTO result = adminInquiryService.getAdminInquiries(status, type, keyword);
        return ResponseEntity.ok(result);
    }

    // 답변 등록
    @PostMapping("inquiries/{inquiryId}/answer")
    public ResponseEntity<InquiryAnswerResponse> createAnswer(
            @PathVariable Long inquiryId,
            @RequestBody InquiryAnswerCreateRequest request) {
        InquiryAnswerResponse response = adminInquiryService.createAnswer(inquiryId, request);
        return ResponseEntity.ok(response);
    }


    // 답변 수정
    @PutMapping("/inquiries/{inquiryId}/answer")
    public ResponseEntity<InquiryAnswerResponse> updateAnswer (
            @PathVariable Long inquiryId,
            @RequestBody InquiryAnswerCreateRequest request) {
        InquiryAnswerResponse response = adminInquiryService.updateAnswer(inquiryId,request);
        return ResponseEntity.ok(response);
    }

    // 문의 삭제
    @DeleteMapping("/inquiries/{inquiryId}")
    public ResponseEntity<String> deleteInquiry (@PathVariable Long inquiryId) {
        adminInquiryService.deleteInquiry(inquiryId);
        return ResponseEntity.ok("삭제완료");
    }




}
