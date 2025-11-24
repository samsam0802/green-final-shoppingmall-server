package kr.kro.moonlightmoist.shopapi.product.dto;

import kr.kro.moonlightmoist.shopapi.brand.dto.BrandDTO;
import kr.kro.moonlightmoist.shopapi.category.dto.CategoryRegisterReq;
import kr.kro.moonlightmoist.shopapi.policy.deliveryPolicy.dto.DeliveryPolicyDTO;
import kr.kro.moonlightmoist.shopapi.product.domain.ExposureStatus;
import kr.kro.moonlightmoist.shopapi.product.domain.SaleStatus;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProductRequest {
    private CategoryRegisterReq category;
    private BrandDTO brand;
    private BasicInfoDTO basicInfo;
    private SaleInfoDTO saleInfo;
    private DeliveryPolicyDTO deliveryPolicy;
    private List<ProductOptionDTO> options;
    private boolean deleted;

}
