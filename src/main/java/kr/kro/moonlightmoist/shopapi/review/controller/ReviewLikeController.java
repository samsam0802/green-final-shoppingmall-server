package kr.kro.moonlightmoist.shopapi.review.controller;

import kr.kro.moonlightmoist.shopapi.review.service.ReviewLikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/like")
public class ReviewLikeController {

    private final ReviewLikeService reviewLikeService;

    @PostMapping("/{reviewId}")
    public boolean toggleLike(@PathVariable("reviewId") Long reviewId) {
        log.info("toggleLike reviewId : {}", reviewId);
        return reviewLikeService.toggleReviewLike(reviewId);
    }

    @GetMapping("/{reviewId}/count")
    public int countLikes(@PathVariable("reviewId") Long reviewId) {
        return reviewLikeService.countReviewLike(reviewId);
    }

}
