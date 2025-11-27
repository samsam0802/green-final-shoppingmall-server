package kr.kro.moonlightmoist.shopapi.product.domain;

import jakarta.persistence.*;
import kr.kro.moonlightmoist.shopapi.product.dto.DetailInfoDTO;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Setter
@Table(name = "product_detail_info")
public class DetailInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public DetailInfoDTO toDTO() {
        return DetailInfoDTO.builder()
                .id(this.id)
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
