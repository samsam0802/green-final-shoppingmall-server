package kr.kro.moonlightmoist.shopapi.policy.deliveryPolicy.repository;

import kr.kro.moonlightmoist.shopapi.policy.deliveryPolicy.domain.DeliveryPolicy;
import kr.kro.moonlightmoist.shopapi.policy.deliveryPolicy.domain.DeliveryPolicyType;
import kr.kro.moonlightmoist.shopapi.util.EntityFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@EnableJpaAuditing
class DeliveryPolicyRepositoryUnitTest {

    @Autowired
    DeliveryPolicyRepository deliveryPolicyRepository;

    @Test
    @DisplayName("배송비정책 등록 테스트")
    public void registerDeliveryPolicy() {
        DeliveryPolicy deliveryPolicy = EntityFactory.createDeliveryPolicy();
        DeliveryPolicy savedDeliveryPolicy = deliveryPolicyRepository.save(deliveryPolicy);

        assertThat(savedDeliveryPolicy.getId()).isNotNull();
        assertThat(savedDeliveryPolicy.getName()).isEqualTo("이름");
        assertThat(savedDeliveryPolicy.getPolicyType()).isEqualTo(DeliveryPolicyType.CONDITIONAL_FREE);
        assertThat(savedDeliveryPolicy.getBasicDeliveryFee()).isEqualTo(3000);
        assertThat(savedDeliveryPolicy.getFreeConditionAmount()).isEqualTo(50000);
        assertThat(savedDeliveryPolicy.isDefaultPolicy()).isTrue();
        assertThat(savedDeliveryPolicy.isDeleted()).isFalse();
        assertThat(savedDeliveryPolicy.getCreatedAt()).isNotNull();
        assertThat(savedDeliveryPolicy.getUpdatedAt()).isNotNull();
    }

}