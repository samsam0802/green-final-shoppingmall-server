package kr.kro.moonlightmoist.shopapi.policy.deliveryPolicy.service;

import kr.kro.moonlightmoist.shopapi.policy.deliveryPolicy.domain.DeliveryPolicy;
import kr.kro.moonlightmoist.shopapi.policy.deliveryPolicy.dto.DeliveryPolicyDTO;
import kr.kro.moonlightmoist.shopapi.policy.deliveryPolicy.repository.DeliveryPolicyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryPolicyServiceImpl implements DeliveryPolicyService{

    private final DeliveryPolicyRepository deliveryPolicyRepository;


    @Override
    public List<DeliveryPolicyDTO> getDeliveryPolicies() {
        List<DeliveryPolicyDTO> policies = deliveryPolicyRepository.findByDeletedFalse()
                .stream().map(entity -> entity.toDTO()).toList();
        return policies;
    }
}
