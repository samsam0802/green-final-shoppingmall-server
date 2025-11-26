package kr.kro.moonlightmoist.shopapi.category.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CategoryRes {
    private Long id;
    private List<CategoryRes> subCategories;
    private String name;
    private int depth;
    private int displayOrder;

}
