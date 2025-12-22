package kr.kro.moonlightmoist.shopapi.review.service;

import jakarta.transaction.Transactional;
import kr.kro.moonlightmoist.shopapi.common.exception.BusinessException;
import kr.kro.moonlightmoist.shopapi.review.domain.Review;
import kr.kro.moonlightmoist.shopapi.review.domain.ReviewComment;
import kr.kro.moonlightmoist.shopapi.review.dto.ReviewCommentDTO;
import kr.kro.moonlightmoist.shopapi.review.exception.reviewcomment.ReviewCommentDeletionException;
import kr.kro.moonlightmoist.shopapi.review.exception.reviewcomment.ReviewCommentEditException;
import kr.kro.moonlightmoist.shopapi.review.exception.reviewcomment.ReviewCommentRegistrationException;
import kr.kro.moonlightmoist.shopapi.review.repository.ReviewCommentRepository;
import kr.kro.moonlightmoist.shopapi.review.repository.ReviewRepository;
import kr.kro.moonlightmoist.shopapi.security.CustomUserDetails;
import kr.kro.moonlightmoist.shopapi.user.domain.User;
import kr.kro.moonlightmoist.shopapi.user.domain.UserRole;
import kr.kro.moonlightmoist.shopapi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ReviewCommentServiceImpl implements ReviewCommentService {

    private final ReviewRepository reviewRepository;
    private final ReviewCommentRepository reviewCommentRepository;
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
    public List<ReviewCommentDTO> getList(Long reviewId) {
        List<ReviewComment> reviewComments = reviewCommentRepository.findByReviewId(reviewId);

        List<ReviewCommentDTO> reviewCommentDTO = reviewComments.stream().map(reviewComment -> {
            return ReviewCommentDTO.builder()
                    .id(reviewComment.getId())
                    .reviewId(reviewComment.getReview().getId())
                    .userId(reviewComment.getUser().getId())
                    .loginId(reviewComment.getUser().getLoginId())
                    .content(reviewComment.getContent())
                    .createAt(reviewComment.getCreatedAt())
                    .deleted(reviewComment.isDeleted())
                    .build();
        }).toList();
        return reviewCommentDTO;
  }

    @Override
    public Long register(ReviewCommentDTO dto) {
        try {
            User user = getLoginUser();
            Review review = reviewRepository.findById(dto.getReviewId())
                    .orElseThrow(() ->
                            new ReviewCommentRegistrationException("리뷰가 없습니다."));

            ReviewComment reviewComment = ReviewComment.builder()
                    .content(dto.getContent())
                    .review(review)
                    .user(user)
                    .build();
            return reviewCommentRepository.save(reviewComment).getId();
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new ReviewCommentRegistrationException();
        }
  }

    @Override
    public ReviewCommentDTO modify(ReviewCommentDTO dto) {
            User user = getLoginUser();
            ReviewComment reviewComment = reviewCommentRepository.findById(dto.getId())
                    .orElseThrow(() -> new ReviewCommentEditException("댓글을 찾을 수 없습니다."));

            //본인 댓글만 수정
            if (!reviewComment.getUser().getId().equals(user.getId())) {
                throw new ReviewCommentEditException("본인의 댓글만 수정할 수 있습니다.");
            }

            reviewComment.changeContent(dto.getContent());

            return ReviewCommentDTO.builder()
                    .id(reviewComment.getId())
                    .content(reviewComment.getContent())
                    .reviewId(reviewComment.getReview().getId())
                    .userId(reviewComment.getUser().getId())
                    .loginId(reviewComment.getUser().getLoginId())
                    .createAt(reviewComment.getCreatedAt())
                    .deleted(reviewComment.isDeleted())
                    .build();
    }

    @Override
    public void remove(Long id) {
        User user = getLoginUser();
        ReviewComment reviewComment = reviewCommentRepository.findById(id)
                .orElseThrow(() -> new ReviewCommentDeletionException("댓글을 찾을 수 없습니다."));

        // 본인 댓글이거나 관리자인 경우 삭제 가능
        boolean isOwner = reviewComment.getUser().getId().equals(user.getId());
        boolean isAdmin = user.getUserRole() == UserRole.ADMIN;

        if (!isOwner && !isAdmin) {
            throw new ReviewCommentDeletionException("본인의 댓글이거나 관리자만 삭제할 수 있습니다.");
        }

        // 이미 삭제된 댓글인지 확인
        if (reviewComment.isDeleted()) {
            throw new ReviewCommentDeletionException("이미 삭제된 댓글입니다.");
        }

        if (isAdmin && !isOwner) {
            // 관리자 삭제
            reviewComment.changeVisible(false);
        } else {
            // 사용자 삭제
            reviewComment.changeDeleted(true);
        }
    }
}
