package kr.kro.moonlightmoist.shopapi.order.dto;

import kr.kro.moonlightmoist.shopapi.order.domain.OrderProductStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderStatusRequest {
    private OrderProductStatus status;
    private String reason;
}
