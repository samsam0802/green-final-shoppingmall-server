package kr.kro.moonlightmoist.shopapi.review.controller;

import kr.kro.moonlightmoist.shopapi.review.dto.ReviewCommentDTO;
import kr.kro.moonlightmoist.shopapi.review.repository.ReviewCommentRepository;
import kr.kro.moonlightmoist.shopapi.review.service.ReviewCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/comment")
@CrossOrigin(origins = "http://localhost:5173")
public class ReviewCommentController {

    private final ReviewCommentService reviewCommentService;

    @GetMapping("/{reviewId}")
    public ResponseEntity<List<ReviewCommentDTO>> getList(@PathVariable("reviewId") Long reviewId) {
      List<ReviewCommentDTO>  reviewComments = reviewCommentService.getList(reviewId);
      return ResponseEntity.ok(reviewComments);
    }

    @PostMapping("/add")
    public ResponseEntity<String> register(@RequestBody ReviewCommentDTO dto) {
      reviewCommentService.register(dto);
      return ResponseEntity.ok("성공");
    }

    @PutMapping("/modify/{commentId}")
    public ResponseEntity<String> modify(
        @PathVariable("commentId") Long commentId,
        @RequestBody ReviewCommentDTO dto,
        @RequestParam Long userId
    ) {

      dto.setId(commentId);
      dto.setUserId(userId);
      reviewCommentService.modify(dto);

      return ResponseEntity.ok("성공");
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<String> remove(
        @PathVariable("commentId") Long commentId,
        @RequestParam Long userId
    ) {
      reviewCommentService.remove(commentId,userId);
      return ResponseEntity.ok("성공");
    }
}
