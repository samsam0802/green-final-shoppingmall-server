package kr.kro.moonlightmoist.shopapi.product.dto;

import kr.kro.moonlightmoist.shopapi.brand.dto.BrandDTO;
import kr.kro.moonlightmoist.shopapi.category.dto.CategoryRegisterReq;
import kr.kro.moonlightmoist.shopapi.product.domain.ExposureStatus;
import kr.kro.moonlightmoist.shopapi.product.domain.SaleStatus;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProductRequest {
    private CategoryRegisterReq category;
    private BrandDTO brand;
    private String productName;
    private String productCode;
    private String searchKeywords;
    private ExposureStatus exposureStatus;
    private SaleStatus saleStatus;
    private String description;
    private boolean cancelable;
    private boolean deleted;

}
