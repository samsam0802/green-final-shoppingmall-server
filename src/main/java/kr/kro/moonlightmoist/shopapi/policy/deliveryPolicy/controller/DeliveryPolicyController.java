package kr.kro.moonlightmoist.shopapi.policy.deliveryPolicy.controller;

import kr.kro.moonlightmoist.shopapi.policy.deliveryPolicy.dto.DeliveryPolicyDTO;
import kr.kro.moonlightmoist.shopapi.policy.deliveryPolicy.service.DeliveryPolicyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deliveryPolicies")
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*",
        methods = {RequestMethod.GET,
                RequestMethod.POST,
                RequestMethod.PUT,
                RequestMethod.DELETE,
                RequestMethod.OPTIONS})
@RequiredArgsConstructor
public class DeliveryPolicyController {

    private final DeliveryPolicyService deliveryPolicyService;

    @GetMapping("")
    public ResponseEntity<List<DeliveryPolicyDTO>> getDeliveryPolicies() {
        List<DeliveryPolicyDTO> policies = deliveryPolicyService.getDeliveryPolicies();
        return ResponseEntity.ok(policies);
    }
}
