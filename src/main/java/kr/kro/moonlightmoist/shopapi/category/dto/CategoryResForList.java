package kr.kro.moonlightmoist.shopapi.category.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CategoryResForList {
    private Long id;
    private List<CategoryResForList> subCategories;
    private String name;
    private int depth;
    private int displayOrder;
}
