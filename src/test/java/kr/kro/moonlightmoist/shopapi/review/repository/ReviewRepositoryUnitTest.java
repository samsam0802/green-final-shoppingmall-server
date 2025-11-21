package kr.kro.moonlightmoist.shopapi.review.repository;

import jakarta.persistence.EntityManager;
import kr.kro.moonlightmoist.shopapi.brand.domain.Brand;
import kr.kro.moonlightmoist.shopapi.brand.repository.BrandRepository;
import kr.kro.moonlightmoist.shopapi.category.domain.Category;
import kr.kro.moonlightmoist.shopapi.category.repository.CategoryRepository;
import kr.kro.moonlightmoist.shopapi.product.domain.Product;
import kr.kro.moonlightmoist.shopapi.product.repository.ProductRepository;
import kr.kro.moonlightmoist.shopapi.review.domain.Review;
import kr.kro.moonlightmoist.shopapi.review.domain.ReviewComment;
import kr.kro.moonlightmoist.shopapi.review.domain.ReviewImage;
import kr.kro.moonlightmoist.shopapi.review.domain.ReviewLike;
import kr.kro.moonlightmoist.shopapi.util.EntityFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@EnableJpaAuditing
public class ReviewRepositoryUnitTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    ReviewLikeRepository reviewLikeRepository;

    @Autowired
    ReviewCommentRepository reviewCommentRepository;

    @Autowired
    EntityManager em;

    private Brand brand;
    private Category category;
    private Product product;
    private ReviewComment reviewComment;
    private ReviewLike reviewLike;
    private Review review;

    @BeforeEach
    public void init() {
        brand = brandRepository.save(EntityFactory.createBrand("브랜드"));
        category = categoryRepository.save(EntityFactory.createCategory("카테고리",0,0));
        product = productRepository.save(EntityFactory.createProduct(category, brand));

        review = Review.builder()
                .content("리뷰내용1")
                .rating(5)
                .visible(true)
                .deleted(false)
                .product(product)
                .build();
        review = reviewRepository.save(review);
        reviewComment = reviewCommentRepository.save(EntityFactory.createReviewComment(review));
        reviewLike = reviewLikeRepository.save(EntityFactory.createReviewLike(review));
    }


    @Test
    @DisplayName("리뷰 이미지 조회 테스트")
    public void addTest(){
        ReviewImage reviewImage = ReviewImage.builder()
                .imageUrl("https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/thumbnails/550/10/0000/0020/A00000020064655ko.jpg?l=ko")
                .build();

        review.addImage(reviewImage);
        reviewRepository.flush();
        em.clear();

        Optional<Review> foundReview = reviewRepository.findById(review.getId());

        assertThat(foundReview).isPresent();
        assertThat(foundReview.get().getId()).isNotNull();
        assertThat(foundReview.get().getContent()).isEqualTo("리뷰내용1");
        assertThat(foundReview.get().getRating()).isEqualTo(5);
        assertThat(foundReview.get().getReviewImages().size()).isEqualTo(1);
        assertThat(foundReview.get().getReviewImages().get(0).getImageUrl()).isEqualTo("https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/thumbnails/550/10/0000/0020/A00000020064655ko.jpg?l=ko");
        assertThat(foundReview.get().getReviewImages().get(0).getImageOrder()).isEqualTo(0);
        assertThat(foundReview.get().isVisible()).isTrue();
        assertThat(foundReview.get().isDeleted()).isFalse();
        assertThat(foundReview.get().getProduct()).isNotNull();
        assertThat(foundReview.get().getProduct().getId()).isEqualTo(product.getId());

        assertThat(reviewComment.getId()).isNotNull();
        assertThat(reviewComment.getContent()).isEqualTo("리뷰댓글");
        assertThat(reviewComment.isVisible()).isTrue();
        assertThat(reviewComment.isDeleted()).isFalse();
        assertThat(reviewComment.getReview()).isNotNull();
        assertThat(reviewComment.getId()).isEqualTo(foundReview.get().getId());

        assertThat(reviewLike.getId()).isNotNull();
        assertThat(reviewLike.isDeleted()).isFalse();
        assertThat(reviewLike.getReview()).isNotNull();
        assertThat(reviewLike.getId()).isEqualTo(foundReview.get().getId());


    }

}
