package kr.kro.moonlightmoist.shopapi.product.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import kr.kro.moonlightmoist.shopapi.product.dto.SaleInfoDTO;
import lombok.*;

@Embeddable
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleInfo {
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ExposureStatus exposureStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SaleStatus saleStatus;

    @Column(name = "is_cancelable", nullable = false)
    private boolean cancelable;

    @Column(name = "use_restock_noti", nullable = false)
    private boolean useRestockNoti;

    @Builder.Default
    @Column(columnDefinition = "integer default 0")
    private int totalSalesCount = 0;


    public void increaseSalesCount(int quantity) {
        totalSalesCount += quantity;
    }

    public SaleInfoDTO toDTO() {
        return SaleInfoDTO.builder()
                .exposureStatus(this.exposureStatus)
                .saleStatus(this.saleStatus)
                .cancelable(this.cancelable)
                .useRestockNoti(this.useRestockNoti)
                .build();
    }
}
