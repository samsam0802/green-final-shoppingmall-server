package kr.kro.moonlightmoist.shopapi.policy.deliveryPolicy.domain;

import jakarta.persistence.*;
import kr.kro.moonlightmoist.shopapi.common.domain.BaseTimeEntity;
import kr.kro.moonlightmoist.shopapi.policy.deliveryPolicy.dto.DeliveryPolicyDTO;
import lombok.*;
import org.hibernate.annotations.Check;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "delivery_policies")
public class DeliveryPolicy extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "policy_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private DeliveryPolicyType policyType;

    @Column(name = "basic_delivery_fee", nullable = false)
    private int basicDeliveryFee;

    @Column(name = "free_condition_amount", nullable = true)
    private Integer freeConditionAmount;

    @Column(name = "is_default", nullable = false)
    private boolean defaultPolicy;

    @Column(name = "is_deleted", nullable = false)
    @Builder.Default
    private boolean deleted = false;

    public DeliveryPolicyDTO toDTO() {
        return DeliveryPolicyDTO.builder()
                .id(this.id)
                .name(this.name)
                .policyType(this.policyType)
                .basicDeliveryFee(this.basicDeliveryFee)
                .freeConditionAmount(this.freeConditionAmount==null ? null : this.freeConditionAmount)
                .defaultPolicy(this.defaultPolicy)
                .build();
    }
}
