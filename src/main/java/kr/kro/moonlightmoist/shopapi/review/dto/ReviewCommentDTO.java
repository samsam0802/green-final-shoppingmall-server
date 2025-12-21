package kr.kro.moonlightmoist.shopapi.review.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class ReviewCommentDTO {
    private Long id;
    private String content;
    private Long reviewId;
    private Long userId;
    private String loginId;
    private LocalDateTime createAt;
    private boolean deleted; //삭제 상태 표시용
    private boolean visible; //관리자 삭제 상태 표시용
}
