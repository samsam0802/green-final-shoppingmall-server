package kr.kro.moonlightmoist.shopapi.review.repository;

import kr.kro.moonlightmoist.shopapi.brand.domain.Brand;
import kr.kro.moonlightmoist.shopapi.category.domain.Category;
import kr.kro.moonlightmoist.shopapi.category.repository.CategoryRepository;
import kr.kro.moonlightmoist.shopapi.product.domain.Product;
import kr.kro.moonlightmoist.shopapi.product.repository.ProductRepository;
import kr.kro.moonlightmoist.shopapi.review.domain.Review;
import kr.kro.moonlightmoist.shopapi.review.domain.ReviewComment;
import kr.kro.moonlightmoist.shopapi.review.domain.ReviewImage;
import kr.kro.moonlightmoist.shopapi.review.domain.ReviewLike;
import kr.kro.moonlightmoist.shopapi.uil.EntityFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@EnableJpaAuditing
public class ReviewRepositoryUnitTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    ReviewLikeRepository reviewLikeRepository;

    @Autowired
    ReviewCommentRepository reviewCommentRepository;

    @Autowired
    ReviewImageRepository reviewImageRepository;

    @Test
    public void addTest(){

//        Brand brand = EntityFactory.createBrand("브랜드1");
//
//        Category category = EntityFactory.createCategory("카테고리1", 0, 0);
//        categoryRepository.save(category);
//
//        EntityFactory.createProduct(category,brand);


        Review review = Review.builder()
                .content("리뷰내용1")
                .rating(5)
                .visible(true)
                .deleted(false)
//                .product(product)
                .build();
        reviewRepository.save(review);

        ReviewLike reviewLike = ReviewLike.builder()
                .review(review)
                .deleted(false)
                .build();
        reviewLikeRepository.save(reviewLike);

        ReviewComment reviewComment = ReviewComment.builder()
                .review(review)
                .visible(true)
                .deleted(false)
                .content("리뷰댓글1")
                .build();
        reviewCommentRepository.save(reviewComment);

        ReviewImage reviewImage = ReviewImage.builder()
                .review(review)
                .imageUrl("https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/thumbnails/550/10/0000/0020/A00000020064655ko.jpg?l=ko")
                .imageOrder(1)
                .fileSize(50)
                .visible(true)
                .fileName("브링그린")
                .deleted(false)
                .build();
        reviewImageRepository.save(reviewImage);
    }
}
