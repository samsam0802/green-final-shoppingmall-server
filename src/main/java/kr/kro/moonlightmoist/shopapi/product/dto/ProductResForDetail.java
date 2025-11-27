package kr.kro.moonlightmoist.shopapi.product.dto;

import kr.kro.moonlightmoist.shopapi.brand.dto.BrandDTO;
import kr.kro.moonlightmoist.shopapi.category.dto.CategoryResForProductDetail;
import kr.kro.moonlightmoist.shopapi.policy.deliveryPolicy.dto.DeliveryPolicyDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductResForDetail {
    private Long id;
    private CategoryResForProductDetail category;
    private BrandDTO brand;
    private BasicInfoDTO basicInfo;
    private SaleInfoDTO saleInfo;
    private DeliveryPolicyDTO deliveryPolicy;
    private List<ProductOptionDTO> options;
    private List<ProductMainImageRes> mainImages;
    private boolean deleted;
}
