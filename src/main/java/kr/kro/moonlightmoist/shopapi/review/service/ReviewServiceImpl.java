package kr.kro.moonlightmoist.shopapi.review.service;

import jakarta.transaction.Transactional;
import kr.kro.moonlightmoist.shopapi.brand.repository.BrandRepository;
import kr.kro.moonlightmoist.shopapi.category.repository.CategoryRepository;
import kr.kro.moonlightmoist.shopapi.product.domain.Product;
import kr.kro.moonlightmoist.shopapi.product.repository.ProductRepository;
import kr.kro.moonlightmoist.shopapi.review.domain.Review;
import kr.kro.moonlightmoist.shopapi.review.domain.ReviewImage;
import kr.kro.moonlightmoist.shopapi.review.dto.ReviewDTO;
import kr.kro.moonlightmoist.shopapi.review.dto.ReviewImageUrlDTO;
import kr.kro.moonlightmoist.shopapi.review.repository.ReviewRepository;
import kr.kro.moonlightmoist.shopapi.user.domain.User;
import kr.kro.moonlightmoist.shopapi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final UserRepository userRepository;

    public Product getProduct() {
        return productRepository.findById(1L).get();
    }

    public User getUser() {
        return userRepository.findById(2L).get();
    }

    @Override
    public List<ReviewDTO> getList(Long productId, String sort) {
        List<Review> reviews = reviewRepository.findByProductId(productId);

        if (sort.equals("latest")) {
            reviews.sort((r1, r2) -> r2.getCreatedAt().compareTo(r1.getCreatedAt()));
        } else if (sort.equals("oldest")) {
            reviews.sort((r1, r2) -> r1.getCreatedAt().compareTo(r2.getCreatedAt()));
        } else if (sort.equals("ratingDesc")) {
            reviews.sort((r1, r2) -> {
                int ratingDesc = r2.getRating() - r1.getRating();
                if (ratingDesc != 0) return ratingDesc;
                return r2.getCreatedAt().compareTo(r1.getCreatedAt());
            }); //내림차순
        } else if (sort.equals("ratingAsc")) {
            reviews.sort((r1, r2) -> {
                int ratingAsc = r1.getRating() - r2.getRating();
                if (ratingAsc != 0) return ratingAsc;
                return r1.getCreatedAt().compareTo(r2.getCreatedAt());
            }); //오름차순
        }

        List<ReviewDTO> reviewDTO = reviews.stream().map(review -> {
            List<String> imageUrls = review.getReviewImages().stream()
                    .map(img -> img.getImageUrl()).toList();
            System.out.println("이미지 URL 리스트: " + imageUrls);

            return ReviewDTO.builder()
                    .id(review.getId())
                    .productId(review.getProduct().getId())
                    .userId(review.getUser().getId())
                    .loginId(review.getUser().getLoginId())
                    .content(review.getContent())
                    .rating(review.getRating())
                    .imageUrls(imageUrls)
                    .createdAt(review.getCreatedAt())
                    .build();
        }).toList();

        return reviewDTO;
    }

    @Override
    public List<ReviewDTO> getListByUser(Long userId) {
        List<Review> list = reviewRepository.findByUserId(userId);

        return list.stream().map(review -> {
            List<String> imageUrls = review.getReviewImages().stream()
                    .map(url -> url.getImageUrl()).toList();

            return ReviewDTO.builder()
                    .id(review.getId())
                    .content(review.getContent())
                    .rating(review.getRating())
                    .userId(review.getUser().getId())
                    .productId(review.getProduct().getId())
                    .createdAt(review.getCreatedAt())
                    .imageUrls(imageUrls)
                    .build();
        }).toList();
    }

    @Override
    public Long register(ReviewDTO dto) {

        Product product = getProduct();
        User user = getUser();

        Review review = Review.builder()
                .user(user)
                .content(dto.getContent())
                .rating(dto.getRating())
                .product(product)
                .build();

        Review reviewSave = reviewRepository.save(review);
        return reviewSave.getId();

    }

    @Override
    public ReviewDTO modify(ReviewDTO reviewDTO) {
        Optional<Review> foundReview = reviewRepository.findById(reviewDTO.getId());
        Review review = foundReview.orElseThrow();

        review.changeContent(reviewDTO.getContent());
        review.changeRating(reviewDTO.getRating());

        if (reviewDTO.getDeleteImgUrls() != null && !reviewDTO.getDeleteImgUrls().isEmpty()) {
            review.removeImgUrls(reviewDTO.getDeleteImgUrls());
        }

        List<String> imageUrls = review.getReviewImages().stream()
                .map(url -> url.getImageUrl())
                .toList();

        return ReviewDTO.builder()
                .content(review.getContent())
                .rating(review.getRating())
                .imageUrls(imageUrls)
                .build();
    }

    @Override
    public void remove(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    @Override
    public void addImageUrls(Long id, ReviewImageUrlDTO dto) {

        Review review = reviewRepository.findById(id).get();

        for(String imageUrl : dto.getImageUrls()) {
            ReviewImage reviewImage = ReviewImage.builder()
                    .imageUrl(imageUrl)
                    .build();
            review.getReviewImages().add(reviewImage);
        }
        log.info("리뷰 ID = {}, 이미지 개수= {}, 결과 = {}",
                id, dto.getImageUrls().size(), review.getReviewImages());
    }

}
