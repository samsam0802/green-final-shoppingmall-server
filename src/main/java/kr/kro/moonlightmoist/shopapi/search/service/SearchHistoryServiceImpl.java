package kr.kro.moonlightmoist.shopapi.search.service;

import kr.kro.moonlightmoist.shopapi.product.domain.Product;
import kr.kro.moonlightmoist.shopapi.product.dto.ProductSearchCondition;
import kr.kro.moonlightmoist.shopapi.product.repository.ProductRepository;
import kr.kro.moonlightmoist.shopapi.review.dto.PageRequestDTO;
import kr.kro.moonlightmoist.shopapi.search.domain.PopularKeyword;
import kr.kro.moonlightmoist.shopapi.search.domain.RecentKeyword;
import kr.kro.moonlightmoist.shopapi.search.dto.SearchPopularKeywordResponseDTO;
import kr.kro.moonlightmoist.shopapi.search.dto.SearchRecentKeywordResponseDTO;
import kr.kro.moonlightmoist.shopapi.search.repository.PopularKeywordRepository;
import kr.kro.moonlightmoist.shopapi.search.repository.RecentKeywordRepository;
import kr.kro.moonlightmoist.shopapi.user.domain.User;
import kr.kro.moonlightmoist.shopapi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchHistoryServiceImpl implements SearchHistoryService{

    private final RecentKeywordRepository recentKeywordRepository;
    private final PopularKeywordRepository popularKeywordRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;


    public User getUser(Long userId) {
        //userId가 null이면 null 반환
        if (userId == null) return null;
        //DB에서 userId로 조회, 없으면 null
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public void searchAdd(Long userId, String guestId, String keyword) {

        if (keyword == null || keyword.trim().isEmpty()) return;

        User user = getUser(userId);

        //RecentKeyword 저장
        RecentKeyword recentKeyword = RecentKeyword.builder()
                .user(user) //회원 ID(로그인 회원이면 값이 있고, 비회원이면 null)
                .guestId(guestId) //비회원 ID(비회원 식별용)
                .keyword(keyword.trim()) //검색어
                .isVisible(true)
                .build();
        recentKeywordRepository.save(recentKeyword);

        //PopularKeyword 갱신
        PopularKeyword popularKeyword = popularKeywordRepository.findById(keyword.trim())
                .orElse(PopularKeyword.builder()
                        .keyword(keyword.trim())
                        .count(0)
                        .lastSearchedAt(LocalDateTime.now())
                        .build());
        popularKeyword.setCount(popularKeyword.getCount() + 1);
        popularKeyword.setLastSearchedAt(LocalDateTime.now());
        popularKeywordRepository.save(popularKeyword);
    }

    @Override
    public List<SearchRecentKeywordResponseDTO> getRecentKeywordList(Long userId, String guestId) {

        List<RecentKeyword> recentKeywords;
        User user = getUser(userId);

        //회원, 비회원 구분
        if (user != null) {
            //회원인 경우 userId 기준 최근 검색어 10개
            recentKeywords  = recentKeywordRepository.findByUserIdRecent(userId);
        } else if (guestId != null) {
            //비회원인 경우 sessionIdentifier 기준 최근 검색어 10개
            recentKeywords = recentKeywordRepository.findByGuestIdRecent(guestId);
        } else {
            recentKeywords = new ArrayList<>();
        }

        List<SearchRecentKeywordResponseDTO> recentKeywordResDTOList = new ArrayList<>();

        for (RecentKeyword rk : recentKeywords) {
            //null이 아니고, 빈 문자열 or 공백이 아닐때
            if (rk.getKeyword() != null && !rk.getKeyword().isBlank()) {

                boolean duplicateCheck = false;
                //SearchRecentKeywordResponseDTO의 키워드와 SearchHistory의 키워드가 같으면
                //내부 for문을 빠져나오고
                for (SearchRecentKeywordResponseDTO dto : recentKeywordResDTOList) {
                    if (dto.getKeyword().equals(rk.getKeyword())) {
                        duplicateCheck = true;
                        break;
                    }
                }
                // duplicateCheck가 true 이므로 continue 실행
                if (duplicateCheck) continue;

                //새로운 keyword면 DTO로 매핑해서 SearchRecentKeywordResponseDTO List에 저장
                SearchRecentKeywordResponseDTO recentKeywordResDTO = SearchRecentKeywordResponseDTO
                        .builder()
                        .keyword(rk.getKeyword())
                        .createdAt(rk.getCreatedAt())
                        .build();
                recentKeywordResDTOList.add(recentKeywordResDTO);

                //최근 검색어 10개를 보여주기 위해
                //SearchRecentKeywordResponseDTO List의 size가 10이 되면
                //break로 for문 종료
                if (recentKeywordResDTOList.size() == 10) break;
            }
        }

        return recentKeywordResDTOList;
    }

    @Override
    public List<SearchPopularKeywordResponseDTO> getPoularKeywordList() {

        List<PopularKeyword> top10 = popularKeywordRepository.findTop10ByOrderByCountDesc();

        List<SearchPopularKeywordResponseDTO> popularKeywordResDTOList = new ArrayList<>();

        for (PopularKeyword p : top10) {
            popularKeywordResDTOList.add(SearchPopularKeywordResponseDTO.builder()
                    .keyword(p.getKeyword())
                    .count(p.getCount())
                    .lastSearchedAt(p.getLastSearchedAt())
                    .build());
        }
        return popularKeywordResDTOList;
    }

    @Override
    public void deleteOneRecentKeyword(Long userId, String guestId, String keyword) {

        User user = getUser(userId);

        List<RecentKeyword> targetKeyword;

        if (user != null) {
            targetKeyword = recentKeywordRepository.findVisibleByUserIdAndKeyword(userId, keyword);
        } else if (guestId != null) {
            targetKeyword = recentKeywordRepository.findVisibleByGuestIdAndKeyword(guestId, keyword);
        } else return;

        for (RecentKeyword rk : targetKeyword) {
            rk.setIsVisible(false); //실제 DB 삭제 대신 숨김 처리
        }
        recentKeywordRepository.saveAll(targetKeyword);
    }

    @Override
    public void deleteAllRecentKeywords(Long userId, String guestId) {

        User user = getUser(userId);

        List<RecentKeyword> targetKeywords;
        if (user != null) {
            targetKeywords = recentKeywordRepository.findByUserIdRecent(userId);
        } else if (guestId != null) {
            targetKeywords = recentKeywordRepository.findByGuestIdRecent(guestId);
        } else return;

        for (RecentKeyword rk : targetKeywords) {
            rk.setIsVisible(false);
        }
        recentKeywordRepository.saveAll(targetKeywords);

    }

    @Override
    public List<Product> searchProductByConditonPage(ProductSearchCondition condition, PageRequestDTO pageRequestDTO) {

        int page = pageRequestDTO.getPage() - 1;
        int size = pageRequestDTO.getSize() == null ? 24 : pageRequestDTO.getSize();

        Pageable pageable = PageRequest.of(page, size);

        List<Product> productPage = productRepository.search(condition);

        return productPage;
    }
}
