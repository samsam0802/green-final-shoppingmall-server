package kr.kro.moonlightmoist.shopapi.cart.controller;

import kr.kro.moonlightmoist.shopapi.cart.dto.CartProductDTO;
import kr.kro.moonlightmoist.shopapi.cart.dto.CartProductListDTO;
import kr.kro.moonlightmoist.shopapi.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    @PostMapping("/change")
    public List<CartProductListDTO> changeCart(@RequestBody CartProductDTO dto) {
        if(dto.getQuantity() <= 0) {
            return cartService.remove(dto.getId());
        }
        return cartService.addOrModify(dto);
    }


}
