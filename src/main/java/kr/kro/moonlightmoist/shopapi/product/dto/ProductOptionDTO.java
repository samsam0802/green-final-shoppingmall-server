package kr.kro.moonlightmoist.shopapi.product.dto;

import kr.kro.moonlightmoist.shopapi.product.domain.ProductOption;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductOptionDTO {
    private String optionName;
    private int purchasePrice;
    private int sellingPrice;
    private int currentStock;
    private int initialStock;
    private int safetyStock;
    private String imageUrl;

    public ProductOption toDomain() {
        return ProductOption.builder()
                .optionName(this.optionName)
                .purchasePrice(this.purchasePrice)
                .sellingPrice(this.sellingPrice)
                .currentStock(this.currentStock)
                .initialStock(this.initialStock)
                .safetyStock(this.safetyStock)
                .imageUrl(this.imageUrl)
                .build();
    }

}
