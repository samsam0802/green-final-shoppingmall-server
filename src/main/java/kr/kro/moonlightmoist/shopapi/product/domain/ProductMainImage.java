package kr.kro.moonlightmoist.shopapi.product.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import kr.kro.moonlightmoist.shopapi.common.domain.BaseTimeEntity;
import lombok.*;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class ProductMainImage {

    @Column(nullable = true)
    private String imageUrl;

    @Column(nullable = true)
    private int displayOrder;

    @Column(nullable = true)
    private ImageType imageType;

    public void setDisplayOrder(int ord) {
        this.displayOrder = ord;
    }

}
