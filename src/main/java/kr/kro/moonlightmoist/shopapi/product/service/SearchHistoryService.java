package kr.kro.moonlightmoist.shopapi.product.service;


import kr.kro.moonlightmoist.shopapi.product.dto.PopularKeywordResponseDTO;
import kr.kro.moonlightmoist.shopapi.product.dto.RecentKeywordResponseDTO;

import java.util.List;

public interface SearchHistoryService {
    //사용자의 검색 기록을 DB에 저장하고, 인기 검색어 카운트를 업데이트
    void recordSearch(Long userId, String sessionIdentifier, String keyword);
    //해당 사용자(회원 또는 비회원)의 최근 검색어 목록 가져오기
    List<RecentKeywordResponseDTO> getRecentKeywordList(Long userId, String sessionIdentifier);
    //실시간 인기 검색어 목록 가져오기
    List<PopularKeywordResponseDTO> getPoularKeywordList();

}
