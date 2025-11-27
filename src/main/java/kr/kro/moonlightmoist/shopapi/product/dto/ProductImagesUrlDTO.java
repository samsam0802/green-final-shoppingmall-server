package kr.kro.moonlightmoist.shopapi.product.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductImagesUrlDTO {
    private List<String> mainImageUrls;
    private List<String> detailImageUrls;
    private List<String> optionImageUrls;
}
