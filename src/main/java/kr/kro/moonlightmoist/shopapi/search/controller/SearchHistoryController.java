package kr.kro.moonlightmoist.shopapi.search.controller;

import jakarta.servlet.http.HttpServletRequest;
import kr.kro.moonlightmoist.shopapi.search.dto.SearchPopularKeywordResponseDTO;
import kr.kro.moonlightmoist.shopapi.search.dto.SearchRecentKeywordResponseDTO;
import kr.kro.moonlightmoist.shopapi.search.service.SearchHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/search")
@CrossOrigin(origins = "http://localhost:5173")
public class SearchHistoryController {

    private final SearchHistoryService searchHistoryService;

    @PostMapping("/add")
    public ResponseEntity<Void> searchAdd(
            @RequestParam String keyword,
            @RequestParam(required = false) Long userId,
            HttpServletRequest request
    ) {
        //HttpSession : 웹에서 사용자를 구분하고 데이터를 잠깐 저장할 수 있는 공간
        //브라우저와 서버가 연결되어 있는 동안 유지되는 데이터 저장소
        //섹션 식별자를 서버가 기억하도록 해주는 역할

        //Filter에서 설정한 UUID
        String guestId = (String) request.getAttribute("guestId");
        searchHistoryService.searchAdd(userId, guestId, keyword);

        log.info("==== add keyword: {}", keyword);
        log.info("==== add userId: {}", userId);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/recent")
    public ResponseEntity<List<SearchRecentKeywordResponseDTO>> getRecentKeywords(
            @RequestParam(required = false) Long userId,
            HttpServletRequest request
    ) {

        String guestId  = (String) request.getAttribute("guestId");
        log.info("==== recent guestId: {}", guestId);

        List<SearchRecentKeywordResponseDTO> recentList =
                searchHistoryService.getRecentKeywordList(userId, guestId);

        return ResponseEntity.ok(recentList);
    }

    @GetMapping("/popular")
    public ResponseEntity<List<SearchPopularKeywordResponseDTO>> getPopularKeywords() {

        List<SearchPopularKeywordResponseDTO> searchPopularKeywordResponseDTO =
                searchHistoryService.getPoularKeywordList();

        return ResponseEntity.ok(searchPopularKeywordResponseDTO);
    }
}
