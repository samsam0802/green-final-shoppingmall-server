package kr.kro.moonlightmoist.shopapi.search.controller;

import jakarta.servlet.http.HttpServletRequest;
import kr.kro.moonlightmoist.shopapi.product.dto.ProductResForList;
import kr.kro.moonlightmoist.shopapi.product.dto.ProductSearchCondition;
import kr.kro.moonlightmoist.shopapi.product.service.ProductService;
import kr.kro.moonlightmoist.shopapi.search.dto.SearchPopularKeywordResponseDTO;
import kr.kro.moonlightmoist.shopapi.search.dto.SearchRecentKeywordResponseDTO;
import kr.kro.moonlightmoist.shopapi.search.service.SearchHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/search")
@CrossOrigin(origins = "http://localhost:5173")
public class SearchHistoryController {

    private final SearchHistoryService searchHistoryService;
    private final ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<Void> searchAdd(
            @RequestParam String keyword,
            @RequestParam(required = false) Long userId,
            HttpServletRequest request
    ) {

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

    @GetMapping("/product")
    public ResponseEntity<List<ProductResForList>> getProductResForList(@RequestParam("keyword") String keyword) {

        ProductSearchCondition condition = new ProductSearchCondition();
        condition.setSearchKeywords(keyword);

        List<ProductResForList> productResForLists = productService.searchProductsByCondition(condition);

        return ResponseEntity.ok(productResForLists);
    }
}
