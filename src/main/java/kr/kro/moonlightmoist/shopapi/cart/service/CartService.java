package kr.kro.moonlightmoist.shopapi.cart.service;

import kr.kro.moonlightmoist.shopapi.cart.domain.CartProduct;
import kr.kro.moonlightmoist.shopapi.cart.dto.CartProductDTO;
import kr.kro.moonlightmoist.shopapi.cart.dto.CartProductListDTO;

import java.util.List;


public interface CartService {
    List<CartProductListDTO> addOrModify(CartProductDTO dto);
    //CartProduct id를 받음
    List<CartProductListDTO> remove(Long cartProductId);
    //user id를 받음
    List<CartProductListDTO> getCartItems(Long userId);
    // 장바구니 전체 비우기
    int removeAllCartItemsOfUser(Long userId);
}
