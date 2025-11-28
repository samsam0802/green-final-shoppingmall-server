package kr.kro.moonlightmoist.shopapi.review.controller;

import kr.kro.moonlightmoist.shopapi.aws.service.S3UploadService;
import kr.kro.moonlightmoist.shopapi.review.domain.ReviewImage;
import kr.kro.moonlightmoist.shopapi.review.dto.ReviewDTO;
import kr.kro.moonlightmoist.shopapi.review.dto.ReviewImageUrlDTO;
import kr.kro.moonlightmoist.shopapi.review.repository.ReviewRepository;
import kr.kro.moonlightmoist.shopapi.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/review")
@CrossOrigin(origins = "http://localhost:5173")
public class ReviewController {

    private final ReviewRepository reviewRepository;
    private final ReviewService reviewService;
    private final S3UploadService s3UploadService;

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ReviewDTO>> getList(
            @PathVariable("productId") Long productId,
            @RequestParam(defaultValue = "sort") String sort
            ){
        List<ReviewDTO> reviews = reviewService.getList(productId, sort);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReviewDTO>> getMyReviews(@PathVariable Long userId){
        List<ReviewDTO> reviews = reviewService.getListByUser(userId);
        return ResponseEntity.ok(reviews);
    }

    @PostMapping("/add")
    public ResponseEntity<String> register(
            @RequestPart("review") ReviewDTO dto,
            @RequestPart(value = "reviewImage", required = false) List<MultipartFile> reviewImage
    ){
        Long id = reviewService.register(dto);

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

        return ResponseEntity.ok("성공");
    }

    @PutMapping("/modify/{reviewId}")
    public ResponseEntity<String> modify(
            @PathVariable("reviewId") Long reviewId,
            @RequestPart("review") ReviewDTO dto,
            @RequestPart(value = "reviewImage", required = false) List<MultipartFile> reviewImage
    ){
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

        return ResponseEntity.ok("성공");
    }

    @DeleteMapping("/delete/{reviewId}")
    public Map<String,String> remove(@PathVariable("reviewId") Long reviewId){
        reviewService.remove(reviewId);
        return Map.of("RESULT","SUCCESS");
    }

    @GetMapping("/product/{productId}/avg")
    public ResponseEntity<Double> getAvgRating(@PathVariable Long productId){
        double avgRating = reviewRepository.reviewAvgRating(productId);
        return ResponseEntity.ok(avgRating);
    }

    @GetMapping("/product/{productId}/count")
    public ResponseEntity<Integer> getReviewCount(@PathVariable Long productId){
        log.info("ProductId for review count: {}", productId);
        int reviewCount = reviewRepository.reviewCount(productId);
        log.info("Review count from repo: {}", reviewCount);
        return ResponseEntity.ok(reviewCount);
    }

    @GetMapping("/product/{productId}/{rating}/count")
    public ResponseEntity<Integer> getRatingBycount(
            @PathVariable Long productId,
            @PathVariable Integer rating
    ){
        int ratingBycount = reviewRepository.ratingByCount(productId, rating);
        return ResponseEntity.ok(ratingBycount);
    }

    @GetMapping("/product/{productId}/positive")
    public ResponseEntity<Integer> getPositiveReview(@PathVariable Long productId){
        int positiveReview = reviewRepository.positiveReview(productId);
        return ResponseEntity.ok(positiveReview);
    }

}
