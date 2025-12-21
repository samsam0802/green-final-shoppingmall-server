package kr.kro.moonlightmoist.shopapi.search.service;

import kr.kro.moonlightmoist.shopapi.product.domain.Product;
import kr.kro.moonlightmoist.shopapi.product.dto.ProductSearchCondition;
import kr.kro.moonlightmoist.shopapi.review.dto.PageRequestDTO;
import kr.kro.moonlightmoist.shopapi.search.dto.SearchPopularKeywordResponseDTO;
import kr.kro.moonlightmoist.shopapi.search.dto.SearchRecentKeywordResponseDTO;

import java.util.List;

public interface SearchHistoryService {
    //사용자의 검색 기록을 DB에 저장하고, 인기 검색어 카운트를 업데이트
    void searchAdd(Long userId, String guestId, String keyword);
    //해당 사용자(회원 또는 비회원)의 최근 검색어 목록 가져오기
    List<SearchRecentKeywordResponseDTO> getRecentKeywordList(Long userId, String guestId);
    //실시간 인기 검색어 목록 가져오기
    List<SearchPopularKeywordResponseDTO> getPoularKeywordList();
    //최근 검색어 개별 삭제
    void deleteOneRecentKeyword(Long userId, String guestId, String keyword);
    //최근 검색어 전체 삭제
    void deleteAllRecentKeywords(Long userId, String guestId);

    List<Product> searchProductByConditonPage(
            ProductSearchCondition condition,
            PageRequestDTO pageRequestDTO
    );
}
