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
@CrossOrigin(origins = "*", allowedHeaders = "*",
        methods = {RequestMethod.GET,
                RequestMethod.POST,
                RequestMethod.PUT,
                RequestMethod.DELETE,
                RequestMethod.OPTIONS
               })
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    @PostMapping("/change")
    public List<CartProductListDTO> changeCart(@RequestBody CartProductDTO dto) {
        log.info("changeCart dto => {}", dto);
        if(dto.getQuantity() <= 0) {
            return cartService.remove(dto.getId());
        }
        return cartService.addOrModify(dto);
    }

    @GetMapping("/items/{userId}")
    public List<CartProductListDTO> getCartItems(@PathVariable Long userId) {
        return cartService.getCartItems(userId);
    }

    @DeleteMapping("/{cartProductId}")
    public List<CartProductListDTO> removeCartItem(@PathVariable Long cartProductId){
        return cartService.remove(cartProductId);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> removeCart(@PathVariable Long userId) {
        return ResponseEntity.ok("삭제된 장바구니 상품 갯수 : " + cartService.removeAllCartItemsOfUser(userId));
    }


}
