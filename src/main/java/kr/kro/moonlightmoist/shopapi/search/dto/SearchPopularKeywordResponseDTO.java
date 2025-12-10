package kr.kro.moonlightmoist.shopapi.search.dto;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SearchPopularKeywordResponseDTO {
    private String keyword;
    private int count;
    private LocalDateTime createdAt;
}
