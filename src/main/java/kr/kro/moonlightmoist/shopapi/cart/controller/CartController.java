package kr.kro.moonlightmoist.shopapi.cart.controller;

import kr.kro.moonlightmoist.shopapi.cart.dto.CartProductDTO;
import kr.kro.moonlightmoist.shopapi.cart.dto.CartProductListDTO;
import kr.kro.moonlightmoist.shopapi.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    @PostMapping("/change")
    public List<CartProductListDTO> changeCartProduct(@RequestBody CartProductDTO dto) {
        if(dto.getQuantity() < 1) {
            cartService.remove(dto.getId());
        }
        return null;
    }

}
