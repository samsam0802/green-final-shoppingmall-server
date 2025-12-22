package kr.kro.moonlightmoist.shopapi.review.repository;

import kr.kro.moonlightmoist.shopapi.review.domain.ReviewComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewCommentRepository extends JpaRepository<ReviewComment,Long> {
    // 삭제되지 않은 댓글만 조회
    @Query("SELECT rc FROM ReviewComment rc WHERE rc.review.id = :reviewId AND rc.deleted = false AND rc.visible = true ORDER BY rc.createdAt ASC")
    List<ReviewComment> findByReviewId(@Param("reviewId") Long reviewId);
}
