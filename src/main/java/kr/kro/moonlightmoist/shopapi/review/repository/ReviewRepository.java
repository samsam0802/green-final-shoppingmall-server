package kr.kro.moonlightmoist.shopapi.review.repository;

import kr.kro.moonlightmoist.shopapi.review.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r WHERE r.user.id = :userId AND r.deleted = false AND r.visible = true")
    Page<Review> findByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT r FROM Review r WHERE r.product.id = :productId AND r.deleted = false AND r.visible = true")
    Page<Review> findByProductId(@Param("productId") Long productId, Pageable pageable);

    //좋아요 개수 기준 정렬 및 페이징 메서드
    @Query(
        value = """
              SELECT r
              FROM Review r
              WHERE r.product.id = :productId
                AND r.deleted = FALSE
                AND r.visible = TRUE
              ORDER BY (
                  SELECT COUNT(rl.id)
                  FROM ReviewLike rl
                  WHERE rl.review = r AND rl.deleted = FALSE
              ) DESC, r.createdAt DESC
          """,
        countQuery = """
              SELECT COUNT(r)
              FROM Review r
              WHERE r.product.id = :productId
                AND r.deleted = FALSE
                AND r.visible = TRUE
          """
    )
    Page<Review> findByProductIdLike(@Param("productId") Long productId, Pageable pageable);

    @Query("select avg(r.rating) from Review r where r.product.id = :productId and r.deleted = false and r.visible = true")
    public Double reviewAvgRating(@Param("productId") Long productId);

    @Query("select count(r.id) from Review r where r.product.id = :productId and r.deleted = false and r.visible = true")
    public int reviewTotalCount(@Param("productId") Long productId);

    @Query("select count(r) from Review r where r.product.id = :productId and r.rating = :rating and r.deleted = false and r.visible = true")
    public int ratingByCount(@Param("productId") Long productId, @Param("rating") int rating);

    @Query("select count(r) from Review r where r.product.id = :productId and r.rating in (4,5) and r.deleted = false and r.visible = true")
    public int positiveReview(@Param("productId") Long productId);
}
