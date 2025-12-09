package kr.kro.moonlightmoist.shopapi.product.service;

import kr.kro.moonlightmoist.shopapi.product.domain.SearchHistory;
import kr.kro.moonlightmoist.shopapi.product.dto.PopularKeywordResponseDTO;
import kr.kro.moonlightmoist.shopapi.product.dto.RecentKeywordResponseDTO;
import kr.kro.moonlightmoist.shopapi.product.repository.SearchHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchHistoryServiceImpl implements SearchHistoryService{

    private final SearchHistoryRepository searchHistoryRepository;

    @Override
    public void recordSearch(Long userId, String sessionIdentifier, String keyword) {
        SearchHistory searchHistory = SearchHistory.builder()
                .userId(userId)
                .sessionIdentifier(sessionIdentifier)
                .keyword(keyword)
                .build();
        searchHistoryRepository.save(searchHistory);
    }

    @Override
    public List<RecentKeywordResponseDTO> getRecentKeywordList(Long userId, String sessionIdentifier) {

        List<SearchHistory> searchHistoryList;

        //회원, 비회원 구분
        if (userId != null) {
            //회원인 경우: userId 기반 조회
            searchHistoryList = searchHistoryRepository.findByUserId10Searched(userId);
        } else if (sessionIdentifier != null) {
            searchHistoryList = searchHistoryRepository.findBySessionIdentifier10Searched(sessionIdentifier);
        }
        
        return null;
    }

    @Override
    public List<PopularKeywordResponseDTO> getPoularKeywordList() {
        return List.of();
    }
}
