package kr.kro.moonlightmoist.shopapi.review.repository;

import kr.kro.moonlightmoist.shopapi.product.domain.Product;
import kr.kro.moonlightmoist.shopapi.product.repository.ProductRepository;
import kr.kro.moonlightmoist.shopapi.review.domain.Review;
import kr.kro.moonlightmoist.shopapi.review.domain.ReviewComment;
import kr.kro.moonlightmoist.shopapi.review.domain.ReviewImage;
import kr.kro.moonlightmoist.shopapi.review.domain.ReviewLike;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;


@SpringBootTest
class ReviewRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    ReviewCommentRepository reviewCommentRepository;

    @Autowired
    ReviewLikeRepository reviewLikeRepository;

    @Autowired
    ReviewImageRepository reviewImageRepository;

    @Test
    public void addTest(){
        Product product = Product.builder()
                .productName("상품1")
                .build();
        productRepository.save(product);

        Review review = Review.builder()
                .content("리뷰1")
                .rating(4)
                .visible(false)
                .deleted(false)
//                .product(product)
                .build();
        reviewRepository.save(review);

        ReviewComment reviewComment = ReviewComment.builder()
                .content("리뷰내용2")
                .visible(false)
                .deleted(false)
                .review(review)
                .build();
        reviewCommentRepository.save(reviewComment);

        ReviewLike reviewLike = ReviewLike.builder()
                .deleted(false)
                .review(review)
                .build();
        reviewLikeRepository.save(reviewLike);

        ReviewImage reviewImage = ReviewImage.builder()
                .imageOrder(1)
                .imageUrl("https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/thumbnails/550/10/0000/0020/A00000020064655ko.jpg?l=ko")
                .fileName("이미지1")
                .fileSize(50)
                .visible(true)
                .deleted(false)
                .review(review)
                .build();
        reviewImageRepository.save(reviewImage);
    }

}