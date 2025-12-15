package kr.kro.moonlightmoist.shopapi.pointHistory.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PointEarnReq {
    private Long userId;
    private Long orderId;
    private Integer pointValue;
}
