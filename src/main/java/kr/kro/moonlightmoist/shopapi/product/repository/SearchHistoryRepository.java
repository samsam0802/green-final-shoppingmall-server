package kr.kro.moonlightmoist.shopapi.product.repository;

import kr.kro.moonlightmoist.shopapi.product.domain.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {

    // 회원 ID를 기준으로 가장 최근 검색한 키워드 10개(중복 포함) 가져오기
    List<SearchHistory> findByUserId10Searched(Long userId);

    // 세션 식별자를 기준으로 가장 최근 검색한 키워드 10개(중복 포함) 가져오기
    List<SearchHistory> findBySessionIdentifier10Searched(String sessionIdentifier);
}
