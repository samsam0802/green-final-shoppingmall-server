package kr.kro.moonlightmoist.shopapi.category.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CategoryResForProductDetail {
    private Long id;
    private CategoryResForProductDetail parent;
    private String name;
    private int depth;
    private int displayOrder;
}
