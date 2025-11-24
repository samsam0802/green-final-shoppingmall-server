package kr.kro.moonlightmoist.shopapi.policy.deliveryPolicy.dto;

import kr.kro.moonlightmoist.shopapi.policy.deliveryPolicy.domain.DeliveryPolicy;
import kr.kro.moonlightmoist.shopapi.policy.deliveryPolicy.domain.DeliveryPolicyType;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DeliveryPolicyDTO {
    private Long id;
    private String name;
    private DeliveryPolicyType policyType;
    private int basicDeliveryFee;
    private Integer freeConditionAmount;
    private boolean defaultPolicy;

    public DeliveryPolicy toEntity() {
        return DeliveryPolicy.builder()
                .name(this.name)
                .policyType(this.policyType)
                .basicDeliveryFee(this.basicDeliveryFee)
                .freeConditionAmount(this.freeConditionAmount)
                .defaultPolicy(this.defaultPolicy)
                .build();
    }
}
