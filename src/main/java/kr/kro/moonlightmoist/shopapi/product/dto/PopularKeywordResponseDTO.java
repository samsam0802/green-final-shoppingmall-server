package kr.kro.moonlightmoist.shopapi.product.dto;


import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PopularKeywordResponseDTO {
    private String keyword;
    private long count;
}
