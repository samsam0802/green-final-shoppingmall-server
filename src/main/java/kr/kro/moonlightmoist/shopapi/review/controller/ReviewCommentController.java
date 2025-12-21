package kr.kro.moonlightmoist.shopapi.review.controller;

import kr.kro.moonlightmoist.shopapi.review.dto.ReviewCommentDTO;
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
public class ReviewCommentController {

    private final ReviewCommentService reviewCommentService;

    @GetMapping("/{reviewId}")
    public ResponseEntity<List<ReviewCommentDTO>> getList(@PathVariable("reviewId") Long reviewId) {
      List<ReviewCommentDTO>  reviewComments = reviewCommentService.getList(reviewId);
      return ResponseEntity.ok(reviewComments);
    }

    @PostMapping("/add")
    public ResponseEntity<Long> register(@RequestBody ReviewCommentDTO dto) {
        Long commentId = reviewCommentService.register(dto);
      return ResponseEntity.ok(commentId);
    }

    @PutMapping("/modify/{commentId}")
    public ResponseEntity<String> modify(@PathVariable("commentId") Long commentId, @RequestBody ReviewCommentDTO dto) {
      dto.setId(commentId);
      reviewCommentService.modify(dto);
      return ResponseEntity.ok("댓글 수정 성공");
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<String> remove(@PathVariable("commentId") Long commentId) {
      reviewCommentService.remove(commentId);
      return ResponseEntity.ok("댓글 삭제 성공");
    }
}
