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
        log.info("리뷰 댓글 목록을 보기 위한 reviewId : {}", reviewId);

        List<ReviewCommentDTO>  reviewComments = reviewCommentService.getList(reviewId);

        return ResponseEntity.ok(reviewComments);
    }

    @PostMapping("/add")
    public ResponseEntity<Long> register(@RequestBody ReviewCommentDTO dto) {
        log.info("댓글 register : {}", dto);

        Long commentId = reviewCommentService.register(dto);
        log.info("등록된 댓글 : {}", commentId);

        return ResponseEntity.ok(commentId);
    }

    @PutMapping("/modify/{commentId}")
    public ResponseEntity<String> modify(@PathVariable("commentId") Long commentId, @RequestBody ReviewCommentDTO dto) {
        dto.setId(commentId);
        log.info("댓글 modify commentId: {}, dto : {}", commentId, dto);

        ReviewCommentDTO reviewCommentModify = reviewCommentService.modify(dto);

        log.info("수정된 댓글 : {}", reviewCommentModify);

        return ResponseEntity.ok("댓글 수정 성공");
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<String> remove(@PathVariable("commentId") Long commentId) {
        log.info("삭제 commentId : {}", commentId);
        reviewCommentService.remove(commentId);
        return ResponseEntity.ok("댓글 삭제 성공");
    }
}
