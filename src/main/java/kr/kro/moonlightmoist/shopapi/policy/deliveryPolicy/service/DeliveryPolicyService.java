package kr.kro.moonlightmoist.shopapi.policy.deliveryPolicy.service;

import kr.kro.moonlightmoist.shopapi.policy.deliveryPolicy.dto.DeliveryPolicyDTO;

import java.util.List;

public interface DeliveryPolicyService {
    List<DeliveryPolicyDTO> getDeliveryPolicies();
}
