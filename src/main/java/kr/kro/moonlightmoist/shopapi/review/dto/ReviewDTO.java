package kr.kro.moonlightmoist.shopapi.review.dto;

import jakarta.persistence.Column;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class ReviewDTO {
    private Long id;
    private String content;
    private int rating;
    private Long userId;
    private String loginId;
    private Long productId;
    private LocalDateTime createdAt;

    private List<String> imageUrls = new ArrayList<>();
    private List<String> deleteImgUrls = new ArrayList<>();
}
