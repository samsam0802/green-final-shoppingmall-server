package kr.kro.moonlightmoist.shopapi.product.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import kr.kro.moonlightmoist.shopapi.common.domain.BaseTimeEntity;
import kr.kro.moonlightmoist.shopapi.product.dto.ProductDetailImageRes;
import kr.kro.moonlightmoist.shopapi.product.dto.ProductMainImageRes;
import lombok.*;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
public class ProductDetailImage {

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private int displayOrder;

    public void setDisplayOrder(int ord) {
        this.displayOrder = ord;
    }

    public ProductDetailImageRes toDTO() {
        return ProductDetailImageRes.builder()
                .imageUrl(this.imageUrl)
                .displayOrder(this.displayOrder)
                .build();
    }
}

