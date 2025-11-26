package kr.kro.moonlightmoist.shopapi.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import kr.kro.moonlightmoist.shopapi.product.domain.ExposureStatus;
import kr.kro.moonlightmoist.shopapi.product.domain.SaleInfo;
import kr.kro.moonlightmoist.shopapi.product.domain.SaleStatus;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleInfoDTO {
    private ExposureStatus exposureStatus;
    private SaleStatus saleStatus;
    @JsonProperty("isCancelable")
    private boolean cancelable;
    private boolean useRestockNoti;

    public SaleInfo toDomain() {
        return SaleInfo.builder()
                .exposureStatus(this.exposureStatus)
                .saleStatus(this.saleStatus)
                .cancelable(this.cancelable)
                .useRestockNoti(this.useRestockNoti)
                .build();
    }
}
