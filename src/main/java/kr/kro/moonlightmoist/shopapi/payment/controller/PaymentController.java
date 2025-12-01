package kr.kro.moonlightmoist.shopapi.payment.controller;

import kr.kro.moonlightmoist.shopapi.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/payments")
public class PaymentController {
    private final PaymentService paymentService;
}
