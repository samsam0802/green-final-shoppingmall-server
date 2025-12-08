package kr.kro.moonlightmoist.shopapi.pointHistory.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointUseReq {
    private Long userId;
    private Integer amountToUse;
}
