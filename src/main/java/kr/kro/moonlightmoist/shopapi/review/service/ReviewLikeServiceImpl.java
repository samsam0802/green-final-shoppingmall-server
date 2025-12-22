package kr.kro.moonlightmoist.shopapi.review.service;

import jakarta.transaction.Transactional;
import kr.kro.moonlightmoist.shopapi.review.domain.Review;
import kr.kro.moonlightmoist.shopapi.review.domain.ReviewLike;
import kr.kro.moonlightmoist.shopapi.review.exception.reviewlike.ReviewLikeException;
import kr.kro.moonlightmoist.shopapi.review.repository.ReviewLikeRepository;
import kr.kro.moonlightmoist.shopapi.review.repository.ReviewRepository;
import kr.kro.moonlightmoist.shopapi.security.CustomUserDetails;
import kr.kro.moonlightmoist.shopapi.user.domain.User;
import kr.kro.moonlightmoist.shopapi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ReviewLikeServiceImpl implements ReviewLikeService{

    private final ReviewRepository reviewRepository;
    private final ReviewLikeRepository reviewLikeRepository;
    private final UserRepository userRepository;

    //로그인 사용자 조회 메서드
    private User getLoginUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof CustomUserDetails)) {
            throw new RuntimeException("로그인 정보가 올바르지 않습니다.");
        }
        String loginId = ((CustomUserDetails) principal).getUsername();
        return userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new RuntimeException("로그인한 사용자를 찾을 수 없습니다."));
    }

    @Override
    public boolean toggleReviewLike(Long reviewId) {
        User user = getLoginUser();
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewLikeException("리뷰를 찾을 수 없습니다."));

        //좋아요(도움이 돼요)가 존재하는지 확인
        Optional<ReviewLike> reviewLike = reviewLikeRepository.findByReviewAndUser(review, user);

        if (reviewLike.isPresent()) {
            //존재하면 삭제 (좋아요 취소)
            reviewLikeRepository.delete(reviewLike.get());
            return false; //취소 상태
        } else {
            //없으면 추가
            ReviewLike like = ReviewLike.builder()
                    .review(review)
                    .user(user)
                    .build();
            reviewLikeRepository.save(like);
            return true; //좋아요(도움이 돼요) 상태
        }
    }

    @Override
    public int countReviewLike(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewLikeException("리뷰를 찾을 수 없습니다."));
        return reviewLikeRepository.countByReviewAndDeletedFalse(review);
    }
}
