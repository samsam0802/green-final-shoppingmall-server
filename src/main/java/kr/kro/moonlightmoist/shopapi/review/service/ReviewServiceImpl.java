package kr.kro.moonlightmoist.shopapi.review.service;

import jakarta.transaction.Transactional;
import kr.kro.moonlightmoist.shopapi.common.exception.BusinessException;
import kr.kro.moonlightmoist.shopapi.order.domain.Order;
import kr.kro.moonlightmoist.shopapi.order.repository.OrderRepository;
import kr.kro.moonlightmoist.shopapi.review.dto.PageRequestDTO;
import kr.kro.moonlightmoist.shopapi.review.dto.PageResponseDTO;
import kr.kro.moonlightmoist.shopapi.product.domain.Product;
import kr.kro.moonlightmoist.shopapi.product.domain.ProductMainImage;
import kr.kro.moonlightmoist.shopapi.product.repository.ProductRepository;
import kr.kro.moonlightmoist.shopapi.review.domain.Review;
import kr.kro.moonlightmoist.shopapi.review.domain.ReviewImage;
import kr.kro.moonlightmoist.shopapi.review.dto.ReviewDTO;
import kr.kro.moonlightmoist.shopapi.review.dto.ReviewImageUrlDTO;
import kr.kro.moonlightmoist.shopapi.review.exception.review.ReviewDeletionException;
import kr.kro.moonlightmoist.shopapi.review.exception.review.ReviewEditException;
import kr.kro.moonlightmoist.shopapi.review.exception.review.ReviewRegistrationException;
import kr.kro.moonlightmoist.shopapi.review.repository.ReviewRepository;
import kr.kro.moonlightmoist.shopapi.security.CustomUserDetails;
import kr.kro.moonlightmoist.shopapi.user.domain.User;
import kr.kro.moonlightmoist.shopapi.user.domain.UserRole;
import kr.kro.moonlightmoist.shopapi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

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
    public PageResponseDTO<ReviewDTO> getList(Long productId, String sort, PageRequestDTO pageRequestDTO) {

        int page = pageRequestDTO.getPage() - 1;
        int size = pageRequestDTO.getSize() == null ? 10 : pageRequestDTO.getSize();

        //Sort 객체 생성
        Sort sortBy = Sort.by("createdAt").descending(); // 기본 정렬: 최신순

        //요청된 sort 값에 따라 Sort 객체 업데이트
        if (sort.equals("latest")) {
            sortBy = Sort.by("createdAt").descending();
        } else if (sort.equals("ratingDesc")) { // 별점높은순 정렬
            // 동일 별점 시 최신순으로 정렬
            sortBy = Sort.by("rating").descending().and(Sort.by("createdAt").descending());
        } else if (sort.equals("ratingAsc")) { // 별점낮은순 정렬
            // 동일 별점 시 최신순으로 정렬
            sortBy = Sort.by("rating").ascending().and(Sort.by("createdAt").descending());
        }

        //Pageable 객체 생성 (page, size, sort 적용)
        Pageable pageable = PageRequest.of(page, size, sortBy);

        //리뷰 조회 실행 (좋아요순은 별도 처리)
        Page<Review> reviewPage;

        if (sort.equals("like")) {
            reviewPage = reviewRepository.findByProductIdLike(productId, PageRequest.of(page, size));
        } else {
            //나머지 정렬 (latest, ratingDesc, ratingAsc)
            reviewPage = reviewRepository.findByProductId(productId, pageable);
        }

        //Page<Review> 데이터를 List<ReviewDTO>로 매핑
        List<ReviewDTO> reviewDTOList = reviewPage.getContent().stream().map(review -> {
            List<String> imageUrls = review.getReviewImages().stream()
                .map(img -> img.getImageUrl()).toList();

            return ReviewDTO.builder()
                .id(review.getId())
                .productId(review.getProduct().getId())
                .userId(review.getUser().getId())
                .loginId(review.getUser().getLoginId())
                .content(review.getContent())
                .rating(review.getRating())
                .createdAt(review.getCreatedAt())
                .imageUrls(imageUrls)
                .build();
        }).toList();

        //PageResponseDTO 반환
        return PageResponseDTO.<ReviewDTO>withAll()
            .dtoList(reviewDTOList)
            .pageRequestDTO(pageRequestDTO)
            .totalDataCount(reviewPage.getTotalElements()) // 전체 데이터 수
            .build();
    }

    @Override
    public PageResponseDTO<ReviewDTO> getListByUser(PageRequestDTO pageRequestDTO) {
        User loginUser = getLoginUser();

        int page = pageRequestDTO.getPage() - 1;
        int size = pageRequestDTO.getSize() == null ? 10 : pageRequestDTO.getSize();

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Review> reviewPage = reviewRepository.findByUserId(loginUser.getId(), pageable);

        List<ReviewDTO> reviewDTOList = reviewPage.getContent().stream()
            .map(review -> {
              //리뷰 이미지 URL
              List<String> imageUrls = review.getReviewImages().stream()
                  .map(url -> url.getImageUrl()).toList();

              //제품 메인 이미지
              List<ProductMainImage> productMainImgs = review.getProduct().getMainImages();
              String productImage = productMainImgs.get(0).getImageUrl();

              //제품명
              String productName = review.getProduct().getBasicInfo().getProductName();

              //브랜드명
              String brandName = review.getProduct().getBrand().getName();

              //주문일자
              LocalDateTime purchaseDate = review.getOrder().getCreatedAt();

              return ReviewDTO.builder()
                  .id(review.getId())
                  .content(review.getContent())
                  .rating(review.getRating())
                  .userId(review.getUser().getId())
                  .productId(review.getProduct().getId())
                  .productImage(productImage)
                  .productName(productName)
                  .brandName(brandName)
                  .createdAt(review.getCreatedAt())
                  .purchaseDate(purchaseDate)
                  .imageUrls(imageUrls)
                  .build();
            }).toList();

        return PageResponseDTO.<ReviewDTO>withAll()
                .dtoList(reviewDTOList)
                .pageRequestDTO(pageRequestDTO)
                .totalDataCount(reviewPage.getTotalElements())
                .build();
    }

    @Override
    public Long register(ReviewDTO dto) {
        try {
            User user = getLoginUser();
            Product product = productRepository.findById(dto.getProductId())
                    .orElseThrow(() ->
                            new ReviewRegistrationException("상품이 없습니다."));
            Order order = orderRepository.findById(dto.getOrderId())
                    .orElseThrow(() ->
                            new ReviewRegistrationException("주문 정보가 없습니다."));
            Review review = Review.builder()
                    .user(user)
                    .content(dto.getContent())
                    .rating(dto.getRating())
                    .product(product)
                    .order(order)
                    .build();
            return reviewRepository.save(review).getId();
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new ReviewRegistrationException();
        }
    }

    @Override
    public ReviewDTO modify(ReviewDTO reviewDTO) {
            Review review = reviewRepository.findById(reviewDTO.getId())
                    .orElseThrow(() -> new ReviewEditException("리뷰를 찾을 수 없습니다."));

            User loginUser = getLoginUser();

            //본인 리뷰만 수정 가능
            if (!review.getUser().getId().equals(loginUser.getId())) {
                throw new ReviewEditException("본인의 리뷰만 수정할 수 있습니다.");
            }

            review.changeContent(reviewDTO.getContent());
            review.changeRating(reviewDTO.getRating());

            if (reviewDTO.getDeleteImgUrls() != null && !reviewDTO.getDeleteImgUrls().isEmpty()) {
                review.removeImgUrls(reviewDTO.getDeleteImgUrls());
            }

            List<String> imageUrls = review.getReviewImages().stream()
                    .map(url -> url.getImageUrl()).toList();

            return ReviewDTO.builder()
                    .content(review.getContent())
                    .rating(review.getRating())
                    .imageUrls(imageUrls)
                    .build();
    }

    @Override
    public void remove(Long reviewId) {
            Review review = reviewRepository.findById(reviewId)
                    .orElseThrow(() -> new ReviewDeletionException("리뷰를 찾을 수 없습니다."));

            User loginUser  = getLoginUser();

            // 본인 리뷰이거나 관리자인 경우 삭제 가능
            boolean isOwner = review.getUser().getId().equals(loginUser.getId());
            boolean isAdmin = loginUser.getUserRole() == UserRole.ADMIN;

            if (!isOwner && !isAdmin) {
                throw new ReviewDeletionException("본인의 리뷰이거나 관리자만 삭제할 수 있습니다.");
            }

            // 이미 삭제된 리뷰인지 확인
            if (review.isDeleted()) {
                throw new ReviewDeletionException("이미 삭제된 리뷰입니다.");
            }

            if (isAdmin && !isOwner) {
                // 관리자 삭제
                review.changeVisible(false);
            } else {
                // 사용자 삭제
                review.changeDeleted(true);
            }
    }

    @Override
    public void addImageUrls(Long id, ReviewImageUrlDTO dto) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewRegistrationException("리뷰를 찾을 수 없습니다."));

        for(String imageUrl : dto.getImageUrls()) {
            ReviewImage reviewImage = ReviewImage.builder()
                    .imageUrl(imageUrl)
                    .build();
            review.getReviewImages().add(reviewImage);
        }
        log.info("리뷰 ID = {}, 이미지 개수= {}, 결과 = {}",
                id, dto.getImageUrls().size(), review.getReviewImages());
    }

    @Override
    public Double getAvgRating(Long productId) {
        return reviewRepository.reviewAvgRating(productId);
    }

    @Override
    public int getReviewTotalCount(Long productId) {
        return reviewRepository.reviewTotalCount(productId);
    }

    @Override
    public int getRatingByCount(Long productId, Integer rating) {
        return reviewRepository.ratingByCount(productId, rating);
    }

    @Override
    public int getPositiveReview(Long productId) {
        return reviewRepository.positiveReview(productId);
    }

}
