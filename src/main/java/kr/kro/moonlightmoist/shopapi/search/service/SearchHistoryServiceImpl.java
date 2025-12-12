package kr.kro.moonlightmoist.shopapi.search.service;

import kr.kro.moonlightmoist.shopapi.search.domain.SearchHistory;
import kr.kro.moonlightmoist.shopapi.search.dto.SearchPopularKeywordResponseDTO;
import kr.kro.moonlightmoist.shopapi.search.dto.SearchRecentKeywordResponseDTO;
import kr.kro.moonlightmoist.shopapi.search.repository.SearchHistoryRepository;
import kr.kro.moonlightmoist.shopapi.user.domain.User;
import kr.kro.moonlightmoist.shopapi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchHistoryServiceImpl implements SearchHistoryService{

    private final SearchHistoryRepository searchHistoryRepository;
    private final UserRepository userRepository;

    public User getUser(Long userId) {
        //userId가 null이면 null 반환
        if (userId == null) return null;
        //DB에서 userId로 조회, 없으면 null
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public void searchAdd(Long userId, String guestId, String keyword) {

        User user = getUser(userId);

        if (keyword == null || keyword.trim().isEmpty()) return;

        //SearchHistory 객체 생성
        SearchHistory searchHistory = SearchHistory.builder()
                .user(user) //회원 ID(로그인 회원이면 값이 있고, 비회원이면 null)
                .guestId(guestId) //비회원 ID(비회원 식별용)
                .keyword(keyword.trim()) //검색어
                .build();
        searchHistoryRepository.save(searchHistory);
    }

    @Override
    public List<SearchRecentKeywordResponseDTO> getRecentKeywordList(Long userId, String guestId) {

        List<SearchHistory> searchHistories;

        User user = getUser(userId);

        //회원, 비회원 구분
        if (user != null) {
            //회원인 경우 userId 기준 최근 검색어 10개
            searchHistories = searchHistoryRepository.findByUserId10Searched(userId);
        } else if (guestId != null) {
            //비회원인 경우 sessionIdentifier 기준 최근 검색어 10개
            searchHistories = searchHistoryRepository.findByGuestId10Searched(guestId);
        } else {
            searchHistories = new ArrayList<>();
        }

        List<SearchRecentKeywordResponseDTO> recentKeywordResDTOList = new ArrayList<>();

        for (SearchHistory keyword : searchHistories) {
            //null이 아니고, 빈 문자열 or 공백이 아닐때
            if (keyword.getKeyword() != null && !keyword.getKeyword().isBlank()) {

                boolean duplicateCheck = false;
                //SearchRecentKeywordResponseDTO의 키워드와 SearchHistory의 키워드가 같으면
                //내부 for문을 빠져나오고
                for (SearchRecentKeywordResponseDTO dto : recentKeywordResDTOList) {
                    if (dto.getKeyword().equals(keyword.getKeyword())) {
                        duplicateCheck = true;
                        break;
                    }
                }
                // duplicateCheck가 true 이므로 continue 실행
                if (duplicateCheck) continue;

                //새로운 keyword면 DTO로 매핑해서 SearchRecentKeywordResponseDTO List에 저장
                SearchRecentKeywordResponseDTO recentKeywordResDTO = SearchRecentKeywordResponseDTO
                        .builder()
                        .keyword(keyword.getKeyword())
                        .createdAt(keyword.getCreatedAt())
                        .build();
                recentKeywordResDTOList.add(recentKeywordResDTO);

                //최근 검색어 10개를 보여주기 위해
                //SearchRecentKeywordResponseDTO List의 size가 10이 되면
                //break로 for문 종료
                if (recentKeywordResDTOList.size() == 10) break;
            }
        }

        return recentKeywordResDTOList;
//        return searchHistories.stream()
//                //keyword.getKeyword() != null -> 검색어가 null이면 false / 검색어가 존재하지 않으므로 필터에서 걸러짐
//                //isBlank()는 문자열이 비어있거나 공백만 있는지 확인하는 메서드, !isBlank()는 공백이 아닌 글자가 있는 문자열을 의미
//                //!keyword.getKeyword().isBlank() -> 빈 문자열이나 공백만 있는 검색어는 제외
//                //null이 아니고, 빈 문자열/공백 문자열이 아닌 검색어만 필터링
//                .filter(keyword -> keyword.getKeyword() != null && !keyword.getKeyword().isBlank())
//                .map(sh -> SearchRecentKeywordResponseDTO.builder()
//                        .keyword(sh.getKeyword())
//                        .createdAt(sh.getCreatedAt())
//                        .build())
//                .distinct() // DTO 기준 중복 제거
//                .limit(10)  // 최근 10개
//                .toList();
    }

    @Override
    public List<SearchPopularKeywordResponseDTO> getPoularKeywordList() {

        //인기 검색어 데이터 가져오기
        //[["키워드1", 카운트]], [["키워드2", 카운트]], [["키워드3", 카운트]]...

        List<Object[]> popularKeywordList = searchHistoryRepository.findPopularKeywords();
        List<SearchPopularKeywordResponseDTO> popularKeywordResDTOList = new ArrayList<>();

        for (int i = 0; i < popularKeywordList.size(); i++) {
            Object[] search = popularKeywordList.get(i);

            //Object 배열 DTO로 변환
            SearchPopularKeywordResponseDTO popularKeyWordResDTO = SearchPopularKeywordResponseDTO
                    .builder()
                    .keyword((String) search[0]) //검색어
                    .count(((Number) search[1]).intValue()) //검색 횟수
                    //Object[]의 count값의 타입이 Long 이므로 (int) primitive 타입으로 형변환 불가능
                    .build();
            popularKeywordResDTOList.add(popularKeyWordResDTO);
        }

        return popularKeywordResDTOList;
//        //Object 배열을 DTO로 변환
//        //popularKeywordList[i][0] = 검색어
//        //popularKeywordList[i][1] = 검색 횟수
//        return popularKeywordList.stream()
//                .map(searchHistory -> SearchPopularKeywordResponseDTO.builder()
//                        .keyword((String) searchHistory[0])
//                        .count(((Number) searchHistory[1]).intValue())
//                        .build())
//                .toList();
//        //[DTO(keyword:"검색어", count=횟수)]
    }
}
