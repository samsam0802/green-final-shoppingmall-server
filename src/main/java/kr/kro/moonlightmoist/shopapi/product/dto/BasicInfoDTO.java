package kr.kro.moonlightmoist.shopapi.product.dto;

import kr.kro.moonlightmoist.shopapi.product.domain.BasicInfo;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasicInfoDTO {
    private String productName;
    private String productCode;
    private String searchKeywords;
    private String description;

    public BasicInfo toDomain() {
        return BasicInfo.builder()
                .productName(this.productName)
                .productCode(this.productCode)
                .searchKeywords(this.searchKeywords)
                .description(this.description)
                .build();
    }
}
