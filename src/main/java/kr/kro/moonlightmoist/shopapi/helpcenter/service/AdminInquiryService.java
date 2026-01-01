package kr.kro.moonlightmoist.shopapi.helpcenter.service;

import kr.kro.moonlightmoist.shopapi.helpcenter.dto.AdminInquiryListDTO;
import kr.kro.moonlightmoist.shopapi.helpcenter.dto.InquiryAnswerCreateRequest;
import kr.kro.moonlightmoist.shopapi.helpcenter.dto.InquiryAnswerResponse;
import kr.kro.moonlightmoist.shopapi.helpcenter.dto.InquiryDTO;

import java.util.List;

public interface AdminInquiryService {
    List<InquiryDTO> readInquiryList ();
    AdminInquiryListDTO getAdminInquiries(String status, String type, String keyword);
    InquiryAnswerResponse createAnswer(Long inquiryId, InquiryAnswerCreateRequest request);
    InquiryAnswerResponse updateAnswer(Long inquiryId, InquiryAnswerCreateRequest request);
    void deleteInquiry(Long inquiryId);

}
