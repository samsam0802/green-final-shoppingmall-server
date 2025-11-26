package kr.kro.moonlightmoist.shopapi.product.dto;

import kr.kro.moonlightmoist.shopapi.product.domain.ProductOption;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductOptionDTO {
    private Long id;
    private String optionName;
    private int purchasePrice;
    private int sellingPrice;
    private int currentStock;
    private int initialStock;
    private int safetyStock;
    private String imageUrl;
    private int displayOrder;

    public ProductOption toDomain() {
        return ProductOption.builder()
                .optionName(this.optionName)
                .purchasePrice(this.purchasePrice)
                .sellingPrice(this.sellingPrice)
                .currentStock(this.currentStock)
                .initialStock(this.initialStock)
                .safetyStock(this.safetyStock)
                .build();
    }

}
