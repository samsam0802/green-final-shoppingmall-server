package kr.kro.moonlightmoist.shopapi.review.controller;

import kr.kro.moonlightmoist.shopapi.aws.service.S3UploadService;
import kr.kro.moonlightmoist.shopapi.review.dto.PageRequestDTO;
import kr.kro.moonlightmoist.shopapi.review.dto.PageResponseDTO;
import kr.kro.moonlightmoist.shopapi.review.dto.ReviewDTO;
import kr.kro.moonlightmoist.shopapi.review.dto.ReviewImageUrlDTO;
import kr.kro.moonlightmoist.shopapi.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/review")
public class ReviewController {

    private final ReviewService reviewService;
    private final S3UploadService s3UploadService;

    @GetMapping("/product/{productId}")
    public ResponseEntity<PageResponseDTO<ReviewDTO>> getList(
            @PathVariable("productId") Long productId,
            @RequestParam(defaultValue = "latest") String sort, //기본 최신순
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
            ) {
        log.info("리뷰 목록 page : {}, size : {}, 정렬 기준 : {}", page, size, sort);

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
            .page(page)
            .size(size)
            .build();

        PageResponseDTO<ReviewDTO> reviews = reviewService.getList(productId, sort, pageRequestDTO);

        log.info("리뷰 목록 : {}", reviews);

        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/user")
    public ResponseEntity<PageResponseDTO<ReviewDTO>> getMyReviews(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
          ) {
        log.info("나의 리뷰 목록 page : {}, size : {}", page, size);

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
            .page(page)
            .size(size)
            .build();

        PageResponseDTO<ReviewDTO> reviews = reviewService.getListByUser(pageRequestDTO);

        log.info("나의 리뷰 목록 : {}", reviews);
        return ResponseEntity.ok(reviews);
    }

    @PostMapping("/add")
    public ResponseEntity<String> register(
            @RequestPart("review") ReviewDTO dto,
            @RequestPart(value = "reviewImage", required = false) List<MultipartFile> reviewImage
    ){
        log.info("리뷰 등록 dto : {}", dto);
        log.info("register 리뷰 이미지 : {}", reviewImage);

        Long id = reviewService.register(dto);
        log.info("reviewService.register : {}", id);

        if (reviewImage != null && !reviewImage.isEmpty()) {
            ReviewImageUrlDTO reviewImageUrlDTO = ReviewImageUrlDTO.builder()
                    .imageUrls(new ArrayList<>())
                    .build();

            for(int i=0; i<reviewImage.size(); i++){
                String url = s3UploadService.uploadOneFile(reviewImage.get(i), "reviews/" + id + "/");
                reviewImageUrlDTO.getImageUrls().add(url);
            }

            reviewService.addImageUrls(id, reviewImageUrlDTO);
        }

        return ResponseEntity.ok("리뷰 등록 성공");
    }

    @PutMapping("/modify/{reviewId}")
    public ResponseEntity<String> modify(
            @PathVariable("reviewId") Long reviewId,
            @RequestPart("review") ReviewDTO dto,
            @RequestPart(value = "reviewImage", required = false) List<MultipartFile> reviewImage
    ){
        log.info("수정하려는 reviewId : {}", reviewId);
        log.info("modify 리뷰 dto : {}", dto);
        log.info("modify 리뷰 이미지 : {}", reviewImage);

        dto.setId(reviewId);
        reviewService.modify(dto);

        if (reviewImage != null && !reviewImage.isEmpty()) {
            ReviewImageUrlDTO reviewImageUrlDTO = ReviewImageUrlDTO.builder()
                    .imageUrls(new ArrayList<>())
                    .build();

            for(int i=0; i<reviewImage.size(); i++){
                String url = s3UploadService.uploadOneFile(reviewImage.get(i),  "reviews/" + reviewId + "/");
                reviewImageUrlDTO.getImageUrls().add(url);
            }

            reviewService.addImageUrls(reviewId, reviewImageUrlDTO);
        }

        return ResponseEntity.ok("리뷰 수정 성공");
    }

    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<String> remove(@PathVariable("reviewId") Long reviewId) {
        log.info("삭제 reviewId : {}", reviewId);

        reviewService.remove(reviewId);

        return ResponseEntity.ok("리뷰 삭제 성공");
    }

    @GetMapping("/product/{productId}/avg")
    public ResponseEntity<Double> getAvgRating(@PathVariable Long productId){
        log.info("product review avgRating productId : {}", productId);

        double avgRating = reviewService.getAvgRating(productId);

        log.info("avgRating : {}", avgRating);
        return ResponseEntity.ok(avgRating);
    }

    @GetMapping("/product/{productId}/count")
    public ResponseEntity<Integer> getReviewCount(@PathVariable Long productId){
        log.info("reviewCount productId : {}", productId);

        int reviewCount = reviewService.getReviewTotalCount(productId);

        log.info("reviewCount : {}", reviewCount);
        return ResponseEntity.ok(reviewCount);
    }

    @GetMapping("/product/{productId}/{rating}/count")
    public ResponseEntity<Integer> getRatingByCount(
            @PathVariable Long productId,
            @PathVariable Integer rating
    ){
        log.info("productId : {}, 상품의 별점(5,4,3,2,1) : {}", productId, rating);

        int ratingBYCount = reviewService.getRatingByCount(productId, rating);

        log.info("ratingByCount : {}", ratingBYCount);
        return ResponseEntity.ok(ratingBYCount);
    }

    @GetMapping("/product/{productId}/positive")
    public ResponseEntity<Integer> getPositiveReview(@PathVariable Long productId){
        log.info("positiveReview productId : {}", productId);

        int positiveReview = reviewService.getPositiveReview(productId);

        log.info("positiveReview : {}", positiveReview);
        return ResponseEntity.ok(positiveReview);
    }

}
