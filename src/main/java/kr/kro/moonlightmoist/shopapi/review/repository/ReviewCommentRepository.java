package kr.kro.moonlightmoist.shopapi.review.repository;

import kr.kro.moonlightmoist.shopapi.review.domain.ReviewComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewCommentRepository extends JpaRepository<ReviewComment,Long> {
    // 삭제되지 않은 댓글만 조회
    @Query("SELECT rc FROM ReviewComment rc WHERE rc.review.id = :reviewId AND rc.deleted = false ORDER BY rc.createdAt ASC")
    List<ReviewComment> findByReviewId(@Param("reviewId") Long reviewId);

    // 삭제되지 않은 댓글 개수
    @Query("SELECT COUNT(rc) FROM ReviewComment rc WHERE rc.review.id = :reviewId AND rc.deleted = false")
    int countByReviewIdAndDeletedFalse(@Param("reviewId") Long reviewId);
}
