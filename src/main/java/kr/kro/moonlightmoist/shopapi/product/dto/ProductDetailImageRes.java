package kr.kro.moonlightmoist.shopapi.product.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductDetailImageRes {
    private String imageUrl;
    private int displayOrder;
}
