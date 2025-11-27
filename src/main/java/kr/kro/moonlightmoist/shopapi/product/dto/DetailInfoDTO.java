package kr.kro.moonlightmoist.shopapi.product.dto;

import kr.kro.moonlightmoist.shopapi.product.domain.DetailInfo;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class DetailInfoDTO {
    private Long id;
    private String capacity;
    private String skinType;
    private String usagePeriod;
    private String usageMethod;
    private String manufacturer;
    private String madeInCountry;
    private String ingredients;
    private String functionalCertification;
    private String caution;
    private String qualityGuarantee;
    private String customerServiceNumber;

    public DetailInfo toEntity() {
        return DetailInfo.builder()
                .capacity(this.capacity)
                .skinType(this.skinType)
                .usagePeriod(this.usagePeriod)
                .usageMethod(this.usageMethod)
                .manufacturer(this.manufacturer)
                .madeInCountry(this.madeInCountry)
                .ingredients(this.ingredients)
                .functionalCertification(this.functionalCertification)
                .caution(this.caution)
                .qualityGuarantee(this.qualityGuarantee)
                .customerServiceNumber(this.customerServiceNumber)
                .build();
    }

}
