package kr.kro.moonlightmoist.shopapi.restockNoti.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RestockNotiReq {
    private Long userId;
    private Long optionId;
}
