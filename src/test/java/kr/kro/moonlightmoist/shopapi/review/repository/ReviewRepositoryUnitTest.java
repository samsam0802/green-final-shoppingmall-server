package kr.kro.moonlightmoist.shopapi.review.repository;

import jakarta.persistence.EntityManager;
import kr.kro.moonlightmoist.shopapi.brand.domain.Brand;
import kr.kro.moonlightmoist.shopapi.brand.repository.BrandRepository;
import kr.kro.moonlightmoist.shopapi.category.domain.Category;
import kr.kro.moonlightmoist.shopapi.category.repository.CategoryRepository;
import kr.kro.moonlightmoist.shopapi.order.domain.Order;
import kr.kro.moonlightmoist.shopapi.order.domain.OrderProduct;
import kr.kro.moonlightmoist.shopapi.order.repository.OrderProductRepository;
import kr.kro.moonlightmoist.shopapi.order.repository.OrderRepository;
import kr.kro.moonlightmoist.shopapi.product.domain.Product;
import kr.kro.moonlightmoist.shopapi.product.domain.ProductOption;
import kr.kro.moonlightmoist.shopapi.product.repository.ProductOptionRepository;
import kr.kro.moonlightmoist.shopapi.product.repository.ProductRepository;
import kr.kro.moonlightmoist.shopapi.review.domain.Review;
import kr.kro.moonlightmoist.shopapi.review.domain.ReviewComment;
import kr.kro.moonlightmoist.shopapi.review.domain.ReviewImage;
import kr.kro.moonlightmoist.shopapi.review.domain.ReviewLike;
import kr.kro.moonlightmoist.shopapi.user.domain.User;
import kr.kro.moonlightmoist.shopapi.user.repository.UserRepository;
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
    OrderProductRepository orderProductRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductOptionRepository productOptionRepository;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    ReviewLikeRepository reviewLikeRepository;

    @Autowired
    ReviewCommentRepository reviewCommentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManager em;

    private Brand brand;
    private Category category;
    private Product product;
    private Order order;
    private ProductOption productOption;
    private OrderProduct orderProduct;
    private ReviewComment reviewComment;
    private ReviewLike reviewLike;
    private Review review;
    private User user;

    @BeforeEach
    public void init() {

        user = userRepository.save(EntityFactory.createUser());
        brand = brandRepository.save(EntityFactory.createBrand("토니모리"));
        category = categoryRepository.save(EntityFactory.createCategory("스킨케어", 1, 2));
        product = productRepository.save(EntityFactory.createProduct(category, brand));
        order = orderRepository.save(EntityFactory.createOrder(user));
        productOption = productOptionRepository.save(EntityFactory.createProductOption("옵션1", product));
        orderProduct = orderProductRepository.save(EntityFactory.createOrderProduct(order, productOption));

        review = Review.builder()
                .user(user)
                .content("리뷰내용1")
                .rating(5)
                .visible(true)
                .deleted(false)
                .orderProduct(orderProduct)
                .build();
        review = reviewRepository.save(review);
        reviewComment = reviewCommentRepository.save(EntityFactory.createReviewComment(user, review));
        reviewLike = reviewLikeRepository.save(EntityFactory.createReviewLike(user, review));
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
        assertThat(foundReview.get().getUser()).isNotNull();
        assertThat(foundReview.get().getContent()).isEqualTo("리뷰내용1");
        assertThat(foundReview.get().getRating()).isEqualTo(5);
        assertThat(foundReview.get().getReviewImages().size()).isEqualTo(1);
        assertThat(foundReview.get().getReviewImages().get(0).getImageUrl()).isEqualTo("https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/thumbnails/550/10/0000/0020/A00000020064655ko.jpg?l=ko");
        assertThat(foundReview.get().getReviewImages().get(0).getImageOrder()).isEqualTo(0);
        assertThat(foundReview.get().isVisible()).isTrue();
        assertThat(foundReview.get().isDeleted()).isFalse();
        assertThat(foundReview.get().getOrderProduct()).isNotNull();
        assertThat(foundReview.get().getOrderProduct().getId()).isEqualTo(product.getId());

        assertThat(reviewComment.getId()).isNotNull();
        assertThat(reviewComment.getUser()).isNotNull();
        assertThat(reviewComment.getContent()).isEqualTo("리뷰댓글");
        assertThat(reviewComment.isVisible()).isTrue();
        assertThat(reviewComment.isDeleted()).isFalse();
        assertThat(reviewComment.getReview()).isNotNull();
        assertThat(reviewComment.getReview().getId()).isEqualTo(foundReview.get().getId());

        assertThat(reviewLike.getId()).isNotNull();
        assertThat(reviewComment.getUser()).isNotNull();
        assertThat(reviewLike.isDeleted()).isFalse();
        assertThat(reviewLike.getReview()).isNotNull();
        assertThat(reviewLike.getReview().getId()).isEqualTo(foundReview.get().getId());
        
    }

}
