package kr.kro.moonlightmoist.shopapi.product.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import kr.kro.moonlightmoist.shopapi.product.dto.BasicInfoDTO;
import lombok.*;

@Embeddable
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasicInfo {

    @Column(unique = true, nullable = false)
    private String productName;

    @Column(unique = false, nullable = true)
    private String productCode;

    private String searchKeywords;

    private String description;

    public BasicInfoDTO toDTO() {
        return BasicInfoDTO.builder()
                .productName(this.productName)
                .productCode(this.productCode)
                .searchKeywords(this.searchKeywords)
                .description(this.description)
                .build();
    }
}
